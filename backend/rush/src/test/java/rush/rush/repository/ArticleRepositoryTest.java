package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.User;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween() {
        // given
        User user = SetUpMethods.persistUser(testEntityManager, "test@email.com");

        // 이것만 해당
        persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        // public 글이 아님
        persistArticle(testEntityManager, user, false, false, 40.63, 127.0);
        // 위도 범위 벗어남
        persistArticle(testEntityManager, user, true, true, 0.0, 126.99);
        // 경도 범위 벗어남
        persistArticle(testEntityManager, user, true, false, 39.95, 5.0);

        // when
        List<Article> articles =
            articleRepository.findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween(
                35.0, 45.0, 125.0, 128.0);

        // then
        assertThat(articles.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void findByPublicMapTrueAndId() {
        // given
        User user = SetUpMethods.persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager, user, true, false, 0.0, 0.0);

        // when
        Optional<Article> foundArticle = articleRepository.findByPublicMapTrueAndId(article.getId());

        // then
        assertThat(foundArticle.isPresent()).isTrue();
        assertThat(foundArticle.get().getId()).isEqualTo(article.getId());
        assertThat(foundArticle.get().getContent()).isEqualTo(article.getContent());
    }
}
