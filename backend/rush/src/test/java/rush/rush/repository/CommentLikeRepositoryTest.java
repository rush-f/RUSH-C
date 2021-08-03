package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistComment;
import static rush.rush.repository.SetUpMethods.persistCommentLike;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import org.junit.jupiter.api.BeforeEach;
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

    User savedUser1;
    User savedUser2;
    Article articleOnPrivateMap;
    Article articleOnPublicMap;

    @BeforeEach
    void setUp(){
        savedUser1 = persistUser(testEntityManager, "test@email.com");
        savedUser2 = persistUser(testEntityManager, "test2@email.com");
        articleOnPrivateMap = persistArticle(testEntityManager, savedUser1, false, true, 0.0, 0.0);
        articleOnPublicMap = persistArticle(testEntityManager, savedUser1, true, false, 0.0, 0.0);
    }

    @Test
    @Transactional
    void findByUserIdAndCommentId(){
        //given
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1, articleOnPublicMap);
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
    void countOfPublicComment() {
        //given
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1, articleOnPublicMap);
        persistCommentLike(testEntityManager, savedUser2, comment);

        testEntityManager.flush();
        testEntityManager.clear();

        //when
        Long count1 = commentLikeRepository.countOfPublicComment(comment.getId(),
            savedUser1.getId());
        Long count2 = commentLikeRepository.countOfPublicComment(comment.getId(),
            savedUser2.getId());

       //then
        assertThat(count1).isEqualTo(0);
        assertThat(count2).isEqualTo(1);
    }
    @Test
    @Transactional
    void countOfPrivateComment() {
        //given
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser1, articleOnPrivateMap);
        persistCommentLike(testEntityManager, savedUser1, comment);
        persistCommentLike(testEntityManager, savedUser2, comment);
        //when
        Long count1 = commentLikeRepository.countOfPrivateComment(comment.getId(),
            savedUser1.getId());
        Long count2 = commentLikeRepository.countOfPrivateComment(comment.getId(),
            savedUser2.getId());
        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(0);
    }

    @Test
    @Transactional
    void countOfGroupedComment() {
        //given
        Group group1=persistGroup(testEntityManager);
        persistArticleGroup(testEntityManager, articleOnPublicMap, group1);
        persistUserGroup(testEntityManager, savedUser2, group1);
        Comment comment = persistComment(testEntityManager, COMMENT_CONTENT, savedUser2, articleOnPublicMap);
        persistCommentLike(testEntityManager, savedUser1, comment);
        persistCommentLike(testEntityManager, savedUser2, comment);

        //when
        Long count1 = commentLikeRepository.countOfGroupedComment(comment.getId(),
            savedUser1.getId());
        Long count2 = commentLikeRepository.countOfGroupedComment(comment.getId(),
            savedUser2.getId());

        //then
        assertThat(count1).isEqualTo(0);
        assertThat(count2).isEqualTo(1);
    }
}