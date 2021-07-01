package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistUser;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.User;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class CommentRepositoryTest {

    public static final String COMMENT_CONTENT = "댓글내용임 ㅇㅇㅇㅇ";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findAllByArticle() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");

        Article article = persistArticle(testEntityManager,
            user, true, true, 37.14, 34.24);

        Comment comment1 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment1);
        Comment comment2 = new Comment(COMMENT_CONTENT, user, article);
        testEntityManager.persist(comment2);

        // when
        List<Comment> comments = commentRepository.findAllByArticleIdOrderByCreateDateDesc(article.getId());
        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).getContent()).isEqualTo(COMMENT_CONTENT);
        assertThat(comments.get(0).getUser().getId()).isEqualTo(user.getId());
        assertThat(comments.get(0).getArticle().getId()).isEqualTo(article.getId());
        assertThat(comments.get(0).getCreateDate()).isEqualTo(comment2.getCreateDate());
        assertThat(comments.get(1).getCreateDate()).isEqualTo(comment1.getCreateDate());
    }
}
