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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.CommentLike;
import rush.rush.domain.Group;
import rush.rush.domain.User;

public class CommentLikeRepositoryTest extends RepositoryTest {

    public static final String COMMENT_CONTENT = "댓글내용임 ㅇㅇㅇㅇ";

    @Autowired
    CommentLikeRepository commentLikeRepository;

    private User savedUser1;
    private User savedUser2;
    private Article articleOnPrivateMap;
    private Article articleOnPublicMap;

    @BeforeEach
    void setUp() {
        savedUser1 = persistUser(testEntityManager, "test@email.com");
        savedUser2 = persistUser(testEntityManager, "test2@email.com");
        articleOnPrivateMap = persistArticle(testEntityManager, savedUser1, false, true, 0.0, 0.0);
        articleOnPublicMap = persistArticle(testEntityManager, savedUser1, true, false, 0.0, 0.0);
    }

    @Test
    @Transactional
    void findByUserIdAndCommentId() {
        //given
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPublicMap);
        persistCommentLike(testEntityManager, savedUser2, comment);

        //when
        CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(savedUser2.getId(),
            comment.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당되는 유저나 댓글이 없어서 테스트 실패"));

        //then
        assertThat(commentLike).isNotNull();
        assertThat(commentLike.getUser().getId()).isEqualTo(savedUser2.getId());
        assertThat(commentLike.getComment().getId()).isEqualTo(comment.getId());
    }

    @Test
    @Transactional
    void findPublicArticleCommentIdsILiked() {
        // given
        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPrivateMap);
        Comment comment2 = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPublicMap);
        Comment comment3 = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPublicMap);
        persistCommentLike(testEntityManager, savedUser2, comment1);
        persistCommentLike(testEntityManager, savedUser2, comment3);

        //when
        List<Long> hasILiked = commentLikeRepository
            .findPublicArticleCommentIdsILiked(articleOnPublicMap.getId(),
                savedUser2.getId());

        //then
        assertThat(hasILiked.get(0)).isEqualTo(comment3.getId());
        assertThat(hasILiked.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName("좋아요를 누른 댓글의 ID 목록 조회 (in any private article)")
    void findPrivateArticleCommentIdsILiked() {
        // given : 댓글이 두 개 존재
        Comment comment1 = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPrivateMap);
        Comment comment2 = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1,
            articleOnPrivateMap);

        // given : 사용자가 댓글1에 좋아요를 남김
        persistCommentLike(testEntityManager, savedUser1, comment1);

        // when & then : 댓글 갯수가 한개고, 해당 댓글은 comment1 임
        List<Long> commentIdsILiked = commentLikeRepository.findPrivateArticleCommentIdsILiked(
            articleOnPrivateMap.getId(), savedUser1.getId());

        assertThat(commentIdsILiked.size()).isEqualTo(1);
        assertThat(commentIdsILiked.get(0)).isEqualTo(comment1.getId());

        // given : 사용자가 댓글2에도 좋아요를 남김
        persistCommentLike(testEntityManager, savedUser1, comment2);

        // when & then : 댓글 갯수가 2개가 됨
        commentIdsILiked = commentLikeRepository.findPrivateArticleCommentIdsILiked(
            articleOnPrivateMap.getId(), savedUser1.getId());

        assertThat(commentIdsILiked.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void findGroupedArticleCommentIdsILiked() {
        //given
        Group group = persistGroup(testEntityManager);

        persistArticleGroup(testEntityManager, articleOnPublicMap, group);
        persistUserGroup(testEntityManager, savedUser2, group);

        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser2,
            articleOnPublicMap);

        persistCommentLike(testEntityManager, savedUser2, comment);

        // when & then : 댓글 좋아요를 한개 눌렀던 사용자
        List<Long> commentIdsUser2Liked = commentLikeRepository.findGroupedArticleCommentIdsILiked(
            articleOnPublicMap.getId(), savedUser2.getId());

        assertThat(commentIdsUser2Liked.size()).isEqualTo(1);
        assertThat(commentIdsUser2Liked.get(0)).isEqualTo(comment.getId());

        // when & then : 댓글 좋아요를 한개도 누르지 않았던 사용자
        List<Long> commentIdsUser1Liked = commentLikeRepository.findGroupedArticleCommentIdsILiked(
            articleOnPublicMap.getId(), savedUser1.getId());

        assertThat(commentIdsUser1Liked.size()).isEqualTo(0);
    }
}