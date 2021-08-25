package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistArticleLike;
import static rush.rush.repository.SetUpMethods.persistComment;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Comment;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.ArticleResponse;

class ArticleRepositoryTest extends RepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Transactional
    void deleteById(@Autowired CommentRepository commentRepository,
        @Autowired ArticleLikeRepository articleLikeRepository) {
        User author = persistUser(testEntityManager, "test1@email.com");
        Group group = persistGroup(testEntityManager);

        // given 글이 작성되어있다.
        Article article = persistArticle(testEntityManager, author, true, true, 37.63, 127.07);
        ArticleGroup articleGroup = persistArticleGroup(testEntityManager, article, group);
        article.addArticleGroup(articleGroup);    // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!

        // given 글에 댓글도 달려있다.
        User another = persistUser(testEntityManager, "test2@email.com");
        Comment comment = persistComment(testEntityManager, "댓글내용", article, another);
        article.addComment(comment);    // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!

        // given 글에 좋아요가 눌러져있다.
        persistArticleLike(testEntityManager, another, article);

        // when
        articleRepository.deleteById(article.getId());

        // then
        assertThat(articleRepository.findAll()).hasSize(0);
        assertThat(commentRepository.findAll()).hasSize(0);
    }

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
    void findAllOfGroupedMap() {
        //given
        User user = persistUser(testEntityManager, "test1@email.com");
        User user2 = persistUser(testEntityManager, "test2@email.com");
        Group group = persistGroup(testEntityManager);

        persistUserGroup(testEntityManager, user, group);

        Article article1 = persistArticle(testEntityManager, user, false, false, 36.0, 127.0);
        Article article2 = persistArticle(testEntityManager, user, false, false, 38.0, 131.0);
        Article article3 = persistArticle(testEntityManager, user, false, false, 35.0, 120.0);
        persistArticleGroup(testEntityManager, article1, group);
        persistArticleGroup(testEntityManager, article2, group);
        persistArticleGroup(testEntityManager, article3, group);

        //when
        List<Article> articles = articleRepository.findAllOfGroupedMap(user.getId(), group.getId(),
            34.0, 37.0, 125.0, 140.0);
        List<Article> articles2 = articleRepository
            .findAllOfGroupedMap(user2.getId(), group.getId(),
                34.0, 37.0, 125.0, 140.0);

        //then
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles2.size()).isEqualTo(0);
        assertThat(articles.get(0).getId()).isEqualTo(article1.getId());
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
        Optional<ArticleResponse> articleResponse = articleRepository
            .findByPublicMapWithLikes(article1.getId());
        Optional<ArticleResponse> articleResponse2 = articleRepository
            .findByPublicMapWithLikes(article2.getId());

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
        Optional<Article> foundArticle = articleRepository
            .findByPublicMapTrueAndId(article.getId());

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
        Optional<Article> foundArticle = articleRepository
            .findByPrivateMapTrueAndIdAndUserId(article.getId(), me.getId());

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
    void findArticlesWithComments() {
        //given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article1 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        persistComment(testEntityManager, "댓글 내용", article1, user);
        persistComment(testEntityManager, "댓글 내용2", article1, user);
        persistComment(testEntityManager, "댓글 내용3", article1, user);
        persistArticleLike(testEntityManager, user, article1);
        persistArticleLike(testEntityManager, user, article1);
        persistArticleLike(testEntityManager, user, article1);
        persistArticleLike(testEntityManager, user, article1);

        testEntityManager.flush();
        testEntityManager.clear();

        // when & then
        List<Article> articles = articleRepository
            .findArticlesWithComments(user.getId());
        Long totalLikes = articles.get(0).getArticleLikes().stream().count();
        Integer totalComments = articles.get(0).getComments().size();
        assertThat(articles).hasSize(1);
        assertThat(totalLikes).isEqualTo(4L);
        assertThat(totalComments).isEqualTo(3);

        // when & then
        Article article2 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article3 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);
        Article article4 = persistArticle(testEntityManager, user, true, true, 37.63, 127.07);

        articles = articleRepository
            .findArticlesWithComments(user.getId());
        assertThat(articles).hasSize(4);
    }

    @Test
    @Transactional
    void findArticleAuthorId() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");
        Article article = persistArticle(testEntityManager, user, true, false, 0.0, 0.0);

        // when
        Optional<Long> articleAuthorId = articleRepository.findArticleAuthorId(article.getId());

        // then
        assertThat(articleAuthorId.isPresent()).isTrue();
        assertThat(articleAuthorId.get()).isEqualTo(user.getId());
    }
}
