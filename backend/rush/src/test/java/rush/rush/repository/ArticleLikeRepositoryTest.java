package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistArticleLike;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.Group;
import rush.rush.domain.User;

class ArticleLikeRepositoryTest  extends RepositoryTest {

    @Autowired
    ArticleLikeRepository articleLikeRepository;

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
        persistArticleLike(testEntityManager, savedUser1, articleOnPublicMap);
        persistArticleLike(testEntityManager, savedUser2, articleOnPublicMap);
        persistArticleLike(testEntityManager, savedUser1, articleOnPrivateMap);
        persistArticleLike(testEntityManager, savedUser2, articleOnPrivateMap);
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

}