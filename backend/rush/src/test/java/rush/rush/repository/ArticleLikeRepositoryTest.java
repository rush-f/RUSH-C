package rush.rush.repository;

import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistUser;

import static org.assertj.core.api.Assertions.assertThat;
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
    Article savedArticle1;
    Article savedArticle2;

    @BeforeEach
    void setUp(){
        savedUser1 = persistUser(testEntityManager, "test@email.com");
        savedUser2 = persistUser(testEntityManager, "test2@email.com");
        savedArticle1 = persistArticle(testEntityManager, savedUser1, false, true, 0.0, 0.0);
        savedArticle2 = persistArticle(testEntityManager, savedUser1, false, true, 0.0, 0.0);
        persistArticleLike(savedUser1, savedArticle1);
        persistArticleLike(savedUser2, savedArticle1);
    }

    @Test
    @Transactional
    void findByUserIdAndArticleId(){
        //given

        //when
        ArticleLike articleLikeTest = articleLikeRepository.findByUserIdAndArticleId(savedUser1.getId(),savedArticle1.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당되는 유저나 게시글이 없어서 테스트 실패"));

        //then
        assertThat(articleLikeTest).isNotNull();
        assertThat(articleLikeTest.getUser().getId()).isEqualTo(savedUser1.getId());
        assertThat(articleLikeTest.getArticle().getId()).isEqualTo(savedArticle1.getId());
    }

    @Test
    @Transactional
    void countByArticleId(){
        //given

        //when
        int totalLikes1 = articleLikeRepository.countByArticleId(savedArticle1.getId());
        int totalLikes2 = articleLikeRepository.countByArticleId(savedArticle2.getId());

        //then
        assertThat(totalLikes1).isEqualTo(2);
        assertThat(totalLikes2).isEqualTo(0);
    }

    @Test
    @Transactional
    void countByUserIdAndArticleId() {
        //given

        //when
        int count1 = articleLikeRepository.countByUserIdAndArticleId(savedUser1.getId(),savedArticle1.getId());
        int count2 = articleLikeRepository.countByUserIdAndArticleId(savedUser2.getId(),savedArticle1.getId());
        int count3 = articleLikeRepository.countByUserIdAndArticleId(savedUser1.getId(),savedArticle2.getId());

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(1);
        assertThat(count3).isEqualTo(0);
    }

    private ArticleLike persistArticleLike(User user, Article article) {
        ArticleLike articleLike =ArticleLike.builder()
            .user(user)
            .article(article)
            .build();
        return testEntityManager.persist(articleLike);
    }
}