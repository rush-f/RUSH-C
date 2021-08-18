package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistComment;
import static rush.rush.repository.SetUpMethods.persistCommentLike;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.CommentLike;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;

class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void deleteById(@Autowired ArticleRepository articleRepository,
        @Autowired CommentRepository commentRepository,
        @Autowired UserGroupRepository userGroupRepository,
        @Autowired CommentLikeRepository commentLikeRepository) {
        User user1 = persistUser(testEntityManager, "test1@email.com");
        User user2 = persistUser(testEntityManager, "test2@email.com");
        Group group = persistGroup(testEntityManager);

        // given user1, user2 각각 게시글을 작성
        Article article1 = persistArticle(testEntityManager, user1, true, true, 0.0, 0.0);
        Article article2 = persistArticle(testEntityManager, user2, true, true, 0.0, 0.0);
        user1.addArticle(article1); // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!
        user2.addArticle(article2);

        // given user1이 group에 가입
        UserGroup userGroup = persistUserGroup(testEntityManager, user1, group);
        user1.addUserGroup(userGroup);    // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!

        // given 게시글에 댓글을 작성
        Comment comment1 = persistComment(testEntityManager, "댓글내용", article2, user1);
        Comment comment2 = persistComment(testEntityManager, "댓글내용", article2, user2);
        Comment comment3 = persistComment(testEntityManager, "댓글내용", article1, user2);
        user1.addComment(comment1);
        article2.addComment(comment1);    // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!
        article2.addComment(comment2);
        article2.addComment(comment3);

        // given 댓글에 좋아요 누름
        CommentLike commentLike = persistCommentLike(testEntityManager, user1, comment2);
        user1.addCommentLike(commentLike);

        // when
        userRepository.deleteById(user1.getId());

        // then
        assertThat(articleRepository.findAll()).hasSize(1);
        assertThat(commentRepository.findAll()).hasSize(1);
        assertThat(userGroupRepository.findAll()).hasSize(0);
        assertThat(commentLikeRepository.findAll()).hasSize(0);
    }

    @Test
    @Transactional
    void findAllByGroupId() {
        // given
        User user1 = persistUser(testEntityManager, "test1@naver.com");
        User user2 = persistUser(testEntityManager, "test2@naver.com");
        User user3 = persistUser(testEntityManager, "test3@naver.com");
        Group group1 = persistGroup(testEntityManager);
        Group group2 = persistGroup(testEntityManager);
        persistUserGroup(testEntityManager, user1, group1);
        persistUserGroup(testEntityManager, user2, group1);
        persistUserGroup(testEntityManager, user3, group2);

        // when
        List<User> group1Members = userRepository.findAllByGroupId(group1.getId());

        // then
        assertThat(group1Members.size()).isEqualTo(2);
    }
}