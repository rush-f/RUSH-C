package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistComment;
import static rush.rush.repository.SetUpMethods.persistCommentLike;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.CommentResponse;

class CommentRepositoryTest extends RepositoryTest {

    public static final String COMMENT_CONTENT = "댓글내용임 ㅇㅇㅇㅇ";

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Transactional
    void countByIdAndUserId() {
        // given : user1이 댓글을 한개 썼다.
        User user1 = persistUser(testEntityManager, "user1@email.com");
        assertThat(user1.getId()).isNotNull();

        User user2 = persistUser(testEntityManager, "user2@email.com");
        assertThat(user2.getId()).isNotNull();

        Article article = persistArticle(testEntityManager, user1, true, false, 37.63, 127.07);
        assertThat(article.getId()).isNotNull();

        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, user1, article);
        assertThat(comment1.getId()).isNotNull();

        // when & then : user1 이 comment1 을 작성했기 때문에 조회결과가 1로 나옴
        assertThat(commentRepository.countByIdAndUserId(comment1.getId(), user1.getId()))
            .isEqualTo(1L);

        // when & then : user2 는 comment1을 작성하지 않았으므로 조회결과가 0
        assertThat(commentRepository.countByIdAndUserId(comment1.getId(), user2.getId()))
            .isEqualTo(0L);
    }

    @Test
    @Transactional
    @DisplayName("전체지도 댓글 한개 조회")
    void findInPublicArticle() {
        // given : 전체지도 글에 댓글이 저장되어있다.
        User user = persistUser(testEntityManager, "test@email.com");
        Article publicArticle = persistArticle(testEntityManager, user, true, false, 37.63, 127.07);
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, user, publicArticle);

        // when
        Optional<Comment> foundComment = commentRepository.findInPublicArticle(comment.getId());

        // then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
    }

    @Test
    @Transactional
    @DisplayName("개인지도 댓글 한개 조회")
    void findInPrivateArticle() {
        // given : 개인지도 글에 댓글이 저장되어있다.
        User user = persistUser(testEntityManager, "test@email.com");
        User another = persistUser(testEntityManager, "test2@email.com");
        Article privateArticle = persistArticle(testEntityManager, user, false, true, 37.63, 127.07);
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, user, privateArticle);

        // when
        Optional<Comment> foundComment = commentRepository.findInPrivateArticle(comment.getId(),
            user.getId());
        Optional<Comment> foundComment2 = commentRepository.findInPrivateArticle(comment.getId(),
            another.getId());

        // then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
        assertThat(foundComment2.isPresent()).isFalse();
    }

    @Test
    @Transactional
    @DisplayName("그룹지도 댓글 한개 조회")
    void findInGroupedArticle() {
        //given
        User user1 = persistUser(testEntityManager, "test1@email.com");
        User user2 = persistUser(testEntityManager, "test2@email.com");
        User another = persistUser(testEntityManager, "test3@email.com");

        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user1, group);
        persistUserGroup(testEntityManager, user2, group);

        Article article = persistArticle(testEntityManager, user1, false, false, 0.0, 0.0);

        persistArticleGroup(testEntityManager, article, group);

        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, user2, article);

        //when
        Optional<Comment> foundComment = commentRepository.findInGroupedArticle(comment.getId(),
            user1.getId());
        Optional<Comment> foundComment2 = commentRepository.findInGroupedArticle(comment.getId(),
            another.getId());

        //then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
        assertThat(foundComment2.isPresent()).isFalse();
    }

    @Test
    @Transactional
    void deleteById() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager,
            user, true, false, 37.14, 34.24);
        Comment comment = persistComment(testEntityManager, "댓글내용", article, user);

        // when
        commentRepository.deleteById(comment.getId());

        // then
        assertThat(commentRepository.findAll()).hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("전체지도 댓글 조회")
    void findAllOfPublicArticle() {
        // given : 전체지도 글에 댓글이 한개 써있다.
        User user = persistUser(testEntityManager, "test@email.com");

        Article publicArticle = persistArticle(testEntityManager,
            user, true, false, 37.14, 34.24);

        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, user, publicArticle);

        persistCommentLike(testEntityManager, user, comment1);
        persistCommentLike(testEntityManager, user, comment1);

        // when & then
        List<CommentResponse> commentResponses = commentRepository.findAllOfPublicArticle(publicArticle.getId());

        assertThat(commentResponses).isNotNull();
        assertThat(commentResponses).hasSize(1);
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(2L);

        // given : 전체지도 글에 댓글이 두개 써있다.
        persistComment(testEntityManager, COMMENT_CONTENT, user, publicArticle);

        // when & then
        assertThat(commentRepository.findAllOfPublicArticle(publicArticle.getId())).hasSize(2);
    }

    @Test
    @Transactional
    @DisplayName("개인지도 댓글 조회")
    void findAllOfPrivateArticle() {
        // given : 개인지도 글에 댓글이 한 개 저장되어 있다.
        User user = persistUser(testEntityManager, "test@email.com");

        Article privateArticle = persistArticle(testEntityManager,
            user, false, true, 37.14, 34.24);

        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, user, privateArticle);
        persistCommentLike(testEntityManager, user, comment1);

        // when & then
        List<CommentResponse> commentResponses = commentRepository.findAllOfPrivateArticle(privateArticle.getId(), user.getId());
        assertThat(commentResponses).isNotNull();
        assertThat(commentResponses).hasSize(1);
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(1L);

        // given : 개인지도 글에 댓글이 두개 저장되어있다.
        persistComment(testEntityManager, COMMENT_CONTENT, user, privateArticle);

        // when & then
        assertThat(commentRepository.findAllOfPrivateArticle(privateArticle.getId(), user.getId()))
            .hasSize(2);
    }

    @Test
    @Transactional
    @DisplayName("개인지도글 댓글 조회 - 내 글이 아닌 개인지도 글 조회 시도시 조회되지 않음")
    void findAllOfPrivateArticle_IfNotHaveAuth_SizeIsZero() {
        // given
        User user = persistUser(testEntityManager, "test1@email.com");
        User another = persistUser(testEntityManager, "test2@email.com");

        Article article = persistArticle(testEntityManager,
            user, false, true, 37.14, 34.24);

        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, user, article);
        Comment comment2 = persistComment(testEntityManager, COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment2);
        persistCommentLike(testEntityManager, user, comment1);
        persistCommentLike(testEntityManager, another, comment1);

        // when
        List<CommentResponse> commentResponses = commentRepository
            .findAllOfPrivateArticle(article.getId(), another.getId());

        // then
        assertThat(commentResponses.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("그룹지도글 댓글 조회")
    void findAllOfGroupedArticle() {
        // given : 그룹지도 글에 댓글이 한개 저장되어있다.
        User user = persistUser(testEntityManager, "test1@email.com");
        User groupMember = persistUser(testEntityManager, "test2@email.com");
        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user, group);
        persistUserGroup(testEntityManager, groupMember, group);

        Article groupedArticle = persistArticle(testEntityManager,
            user, false, false, 37.14, 34.24);
        persistArticleGroup(testEntityManager, groupedArticle, group);

        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, user, groupedArticle);
        persistCommentLike(testEntityManager, user, comment1);

        // when & then
        List<CommentResponse> commentResponses = commentRepository.findAllOfGroupedArticle(
            groupedArticle.getId(), groupMember.getId());

        assertThat(commentResponses.size()).isEqualTo(1);
        assertThat(commentResponses.get(0).getId()).isEqualTo(comment1.getId());
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(1L);

         // given : 그룹지도 글에 댓글이 두개 저장되어있다.
        persistComment(testEntityManager, COMMENT_CONTENT, user, groupedArticle);

        // when & then
        commentResponses = commentRepository.findAllOfGroupedArticle(
            groupedArticle.getId(), groupMember.getId());
        assertThat(commentResponses.size()).isEqualTo(2);
    }
}
