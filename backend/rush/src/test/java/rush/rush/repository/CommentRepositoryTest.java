package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
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
    @DisplayName("전체지도 댓글 한개 조회")
    void findAsPublicArticle(){
        //given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager, user, true, false, 37.63, 127.07);
        Comment comment = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment);

        //when
        Optional<Comment> foundComment = commentRepository.findAsPublicArticle(comment.getId());

        //then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
    }

    @Test
    @Transactional
    @DisplayName("개인지도 댓글 한개 조회")
    void findAsPrivateArticle(){
        //given
        User user = persistUser(testEntityManager, "test@email.com");
        User another = persistUser(testEntityManager, "test2@email.com");
        Article article = persistArticle(testEntityManager, user, false, true, 37.63, 127.07);
        Comment comment = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment);

        //when
        Optional<Comment> foundComment = commentRepository.findAsPrivateArticle(comment.getId(), user.getId());
        Optional<Comment> foundComment2 = commentRepository.findAsPrivateArticle(comment.getId(), another.getId());

        //then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
        assertThat(foundComment2.isPresent()).isFalse();
    }

    @Test
    @Transactional
    @DisplayName("그룹지도 댓글 한개 조회")
    void findAsGroupedArticle(){
        //given
        User user1 = persistUser(testEntityManager, "test1@email.com");
        User user2 = persistUser(testEntityManager, "test2@email.com");
        User another = persistUser(testEntityManager, "test3@email.com");

        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user1, group);
        persistUserGroup(testEntityManager, user2, group);

        Article article = persistArticle(testEntityManager, user1, false, false, 0.0, 0.0);

        persistArticleGroup(testEntityManager, article, group);

        Comment comment = new Comment(COMMENT_CONTENT, user2, article);
        testEntityManager.persist(comment);

        //when
        Optional<Comment> foundComment = commentRepository.findAsGroupedArticle(comment.getId(), user1.getId());
        Optional<Comment> foundComment2 = commentRepository.findAsGroupedArticle(comment.getId(), another.getId());

        //then
        assertThat(foundComment.isPresent()).isTrue();
        assertThat(foundComment.get().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.get().getContent()).isEqualTo(comment.getContent());
        assertThat(foundComment2.isPresent()).isFalse();

    }

    @Test
    @Transactional
    @DisplayName("전체지도 댓글 조회")
    void findAllOfPublicArticle() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");

        Article article = persistArticle(testEntityManager,
            user, true, false, 37.14, 34.24);

        Comment comment1 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment1);
        Comment comment2 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment2);
        persistCommentLike(testEntityManager, user, comment1);
        persistCommentLike(testEntityManager, user, comment1);

        // when
        List<CommentResponse> commentResponses = commentRepository.findAllOfPublicArticle(article.getId());

        // then
        assertThat(commentResponses).isNotNull();
        assertThat(commentResponses).hasSize(2);
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(0);
        assertThat(commentResponses.get(1).getTotalLikes()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("개인지도 댓글 조회")
    void findAllOfPrivateArticle() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");

        Article article = persistArticle(testEntityManager,
            user, false, true, 37.14, 34.24);

        Comment comment1 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment1);
        Comment comment2 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment2);
        persistCommentLike(testEntityManager, user, comment1);
        persistCommentLike(testEntityManager, user, comment1);


        // when
        List<CommentResponse> commentResponses = commentRepository.findAllOfPrivateArticle(article.getId(), user.getId());

        // then
        assertThat(commentResponses).isNotNull();
        assertThat(commentResponses).hasSize(2);
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(0);
        assertThat(commentResponses.get(1).getTotalLikes()).isEqualTo(2);
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

        Comment comment1 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment1);
        Comment comment2 = new Comment(COMMENT_CONTENT, user, article);
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
        // given
        User user = persistUser(testEntityManager, "test1@email.com");
        User groupMember = persistUser(testEntityManager, "test2@email.com");
        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user, group);
        persistUserGroup(testEntityManager, groupMember, group);

        Article article = persistArticle(testEntityManager,
            user, false, false, 37.14, 34.24);
        persistArticleGroup(testEntityManager, article, group);

        Comment comment1 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment1);
        Comment comment2 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment2);
        persistCommentLike(testEntityManager, user, comment1);
        persistCommentLike(testEntityManager, groupMember, comment1);

        // when
        List<CommentResponse> commentResponses = commentRepository
            .findAllOfGroupedArticle(article.getId(), groupMember.getId());

        // then
        assertThat(commentResponses.size()).isEqualTo(2);
        assertThat(commentResponses.get(0).getTotalLikes()).isEqualTo(0);
        assertThat(commentResponses.get(1).getTotalLikes()).isEqualTo(2);
    }
}
