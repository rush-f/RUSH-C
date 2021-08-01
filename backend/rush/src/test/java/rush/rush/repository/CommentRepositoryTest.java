package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.Group;
import rush.rush.domain.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql("/init-table.sql")
class CommentRepositoryTest {

    public static final String COMMENT_CONTENT = "댓글내용임 ㅇㅇㅇㅇ";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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

        // when
        List<Comment> comments = commentRepository.findAllOfPublicArticle(article.getId());

        // then
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
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

        // when
        List<Comment> comments = commentRepository.findAllOfPrivateArticle(article.getId(), user.getId());

        // then
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
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

        // when
        List<Comment> comments = commentRepository
            .findAllOfPrivateArticle(article.getId(), another.getId());

        // then
        assertThat(comments.isEmpty()).isTrue();
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

        // when
        List<Comment> comments = commentRepository
            .findAllOfGroupedArticle(article.getId(), groupMember.getId());

        // then
        assertThat(comments.size()).isEqualTo(2);
    }
}
