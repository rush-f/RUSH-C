package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistArticleLike;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.ArticleResponse;

class ArticleRepositoryTest extends RepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

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
    void findByPublicMapWithLikes() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article1 = persistArticle(testEntityManager, user, true, false, 0.0, 0.0);
        Article article2 = persistArticle(testEntityManager, user, true, false, 0.0, 0.0);
        persistArticleLike(testEntityManager, user, article2);
        persistArticleLike(testEntityManager, user, article2);
        persistArticleLike(testEntityManager, user, article2);

        // when
        Optional<ArticleResponse> articleResponse = articleRepository.findByPublicMapWithLikes(article1.getId());
        Optional<ArticleResponse> articleResponse2 = articleRepository.findByPublicMapWithLikes(article2.getId());

        // then
        assertThat(articleResponse.isPresent()).isTrue();
        assertThat(articleResponse.get().getId()).isEqualTo(article1.getId());
        assertThat(articleResponse.get().getContent()).isEqualTo(article1.getContent());
        assertThat(articleResponse.get().getTotalLikes()).isEqualTo(0);
        assertThat(articleResponse2.get().getTotalLikes()).isEqualTo(3);
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
    void findAsGroupMapArticle() {
        // given
        User user1 = persistUser(testEntityManager, "test1@email.com");
        User user2 = persistUser(testEntityManager, "test2@email.com");
        User anotherGroupUser = persistUser(testEntityManager, "test3@email.com");

        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user1, group);
        persistUserGroup(testEntityManager, user2, group);

        Article article = persistArticle(testEntityManager, user1, false, false, 0.0, 0.0);

        persistArticleGroup(testEntityManager, article, group);

        // when
        Optional<Article> foundArticle1 = articleRepository.findAsGroupMapArticle(
            article.getId(), user2.getId());
        // then
        assertThat(foundArticle1.isPresent()).isTrue();
        assertThat(foundArticle1.get().getId()).isEqualTo(article.getId());

        // when
        Optional<Article> foundArticle2 = articleRepository.findAsGroupMapArticle(
            article.getId(), anotherGroupUser.getId());
        // then
        assertThat(foundArticle2.isPresent()).isFalse();
    }

    @Test
    @Transactional
    void findArticlesWithGroupsByUserId(){
        //given
        User user = persistUser(testEntityManager, "test@email.com");

        Group group1 = persistGroup(testEntityManager);
        Group group2 = persistGroup(testEntityManager);
        Group group3 = persistGroup(testEntityManager);

        Article article1 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article2 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article3 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article4 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);

        persistArticleGroup(testEntityManager, article3, group1);
        persistArticleGroup(testEntityManager, article3, group2);
        persistArticleGroup(testEntityManager, article4, group3);

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

        assertThat(articles.size()).isEqualTo(4);
        assertThat(groups2.size()).isEqualTo(1);
        assertThat(groups1.size()).isEqualTo(2);
    }
}
