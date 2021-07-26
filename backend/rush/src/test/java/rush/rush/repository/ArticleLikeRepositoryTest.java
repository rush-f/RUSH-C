package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.Group;
import rush.rush.domain.User;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class ArticleLikeRepositoryTest {

    @Autowired
    ArticleLikeRepository articleLikeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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
        persistArticleLike(savedUser1, articleOnPublicMap);
        persistArticleLike(savedUser2, articleOnPublicMap);
        persistArticleLike(savedUser1, articleOnPrivateMap);
        persistArticleLike(savedUser2, articleOnPrivateMap);
    }

    @Test
    @Transactional
    void findByUserIdAndArticleId(){
        //given

        //when
        ArticleLike articleLikeTest = articleLikeRepository.findByUserIdAndArticleId(savedUser1.getId(),
            articleOnPrivateMap.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당되는 유저나 게시글이 없어서 테스트 실패"));

        //then
        assertThat(articleLikeTest).isNotNull();
        assertThat(articleLikeTest.getUser().getId()).isEqualTo(savedUser1.getId());
        assertThat(articleLikeTest.getArticle().getId()).isEqualTo(articleOnPrivateMap.getId());
    }

    @Test
    @Transactional
    void countByArticleId(){
        //given

        //when
        Long totalLikes1 = articleLikeRepository.countByArticleId(articleOnPrivateMap.getId());
        Long totalLikes2 = articleLikeRepository.countByArticleId(articleOnPublicMap.getId());

        //then
        assertThat(totalLikes1).isEqualTo(2);
        assertThat(totalLikes2).isEqualTo(2);
    }

    @Test
    @Transactional
    void countOfPublicArticle() {
        //given

        //when
        Long count1 = articleLikeRepository.countOfPublicArticle(articleOnPublicMap.getId(),
            savedUser1.getId());
        Long count2 = articleLikeRepository.countOfPublicArticle(articleOnPublicMap.getId(),
            savedUser2.getId());

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(1);

    }

    @Test
    @Transactional
    void countOfPrivateArticle() {
        //given

        //when
        Long count1 = articleLikeRepository.countOfPrivateArticle(articleOnPrivateMap.getId(),
            savedUser1.getId());
        Long count2 = articleLikeRepository.countOfPrivateArticle(articleOnPrivateMap.getId(),
            savedUser2.getId());

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(0);
    }

    @Test
    @Transactional
    void countOfGroupedArticle() {
        //given
        Group group1=persistGroup(testEntityManager);
        persistArticleGroup(testEntityManager, articleOnPublicMap, group1);
        persistUserGroup(testEntityManager, savedUser2, group1);

        //when
        Long count1 = articleLikeRepository.countOfGroupedArticle(articleOnPublicMap.getId(),
            savedUser1.getId());
        Long count2 = articleLikeRepository.countOfGroupedArticle(articleOnPublicMap.getId(),
            savedUser2.getId());

        //then
        assertThat(count1).isEqualTo(0);
        assertThat(count2).isEqualTo(1);
    }

    private ArticleLike persistArticleLike(User user, Article article) {
        ArticleLike articleLike =ArticleLike.builder()
            .user(user)
            .article(article)
            .build();
        return testEntityManager.persist(articleLike);
    }
}