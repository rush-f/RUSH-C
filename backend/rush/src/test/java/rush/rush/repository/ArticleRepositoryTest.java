package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
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
    void findAllByUserId() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");

        persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        persistArticle(testEntityManager, user, false, false, 40.63, 127.0);
        persistArticle(testEntityManager, user, false, true, 40.63, 127.0);
        persistArticle(testEntityManager, user, false, false, 40.63, 127.0);
        // when
        List<Article> articles =
            articleRepository.findAllByUserId(user.getId());

        // then
        assertThat(articles.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    void findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");

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
    void findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween() {
        // given
        User me = persistUser(testEntityManager, "me@email.com");
        User another = persistUser(testEntityManager, "another@email.com");

        persistArticle(testEntityManager, me, true, true, 37.63, 127.07);
        persistArticle(testEntityManager, me, false, true, 40.63, 127.0);
        persistArticle(testEntityManager, me, true, false, 40.63, 127.0);
        persistArticle(testEntityManager, me, true, true, 0.0, 126.99);
        persistArticle(testEntityManager, me, true, false, 39.95, 5.0);
        persistArticle(testEntityManager, another, false, true, 40.63, 127.0);

        // when
        List<Article> articles =
            articleRepository.findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween(
                me.getId(), 35.0, 45.0, 125.0, 128.0);

        // then
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void findByPublicMapTrueAndId() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager, user, true, false, 0.0, 0.0);

        // when
        Optional<Article> foundArticle = articleRepository.findByPublicMapTrueAndId(article.getId());

        // then
        assertThat(foundArticle.isPresent()).isTrue();
        assertThat(foundArticle.get().getId()).isEqualTo(article.getId());
        assertThat(foundArticle.get().getContent()).isEqualTo(article.getContent());
    }

    @Test
    @Transactional
    void findByPrivateMapTrueAndIdAndUserId() {
        // given
        User me = persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager, me, false, true, 0.0, 0.0);

        // when
        Optional<Article> foundArticle = articleRepository.findByPrivateMapTrueAndIdAndUserId(article.getId(), me.getId());

        // then
        assertThat(foundArticle.isPresent()).isTrue();
        assertThat(foundArticle.get().getId()).isEqualTo(article.getId());
        assertThat(foundArticle.get().getContent()).isEqualTo(article.getContent());
    }

    @Test
    @Transactional
    void findArticlesWithGroupsByUserId(){
        //given
        User user = persistUser(testEntityManager, "test@email.com");

        Group group1 = persistGroup();
        Group group2 = persistGroup();
        Group group3 = persistGroup();

        persistUserGroup(testEntityManager, user, group1);
        persistUserGroup(testEntityManager, user, group2);

        Article article1 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article2 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);

        persistArticleGroup(article1, group1);
        persistArticleGroup(article1, group2);
        persistArticleGroup(article2, group3);

        testEntityManager.flush();
        testEntityManager.clear();

        // when
        List<Article> articles = articleRepository.findArticlesWithGroupsByUserId(user.getId());

        //then
        List<Group> groups2 = articles.get(0)
            .getArticleGroups()
            .stream()
            .map(ArticleGroup::getGroup)
            .collect(Collectors.toList());

        List<Group> groups1 = articles.get(1)
            .getArticleGroups()
            .stream()
            .map(ArticleGroup::getGroup)
            .collect(Collectors.toList());

        assertThat(groups2.size()).isEqualTo(1);
        assertThat(groups1.size()).isEqualTo(2);
    }

    private Group persistGroup() {
        Group group = Group.builder()
            .name(Constants.TEST_GROUP_NAME)
            .build();
        return testEntityManager.persist(group);
    }

    private void persistArticleGroup(Article article, Group group) {
        ArticleGroup articleGroup = ArticleGroup.builder()
            .group(group)
            .article(article)
            .build();
        testEntityManager.persist(articleGroup);
    }
}
