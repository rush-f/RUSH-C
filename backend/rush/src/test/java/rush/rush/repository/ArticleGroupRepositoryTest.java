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
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
import rush.rush.domain.User;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
public class ArticleGroupRepositoryTest {

    @Autowired
    private ArticleGroupRepository articleGroupRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findAllByArticleId() {
        // given
        User me = persistUser(testEntityManager, "me@email.com");

        Article article1 = persistArticle(testEntityManager, me, true, true, 37.63, 127.07);
        Article article2 = persistArticle(testEntityManager, me, false, false, 40.63, 127.0);
        Article article3 = persistArticle(testEntityManager, me, false, false, 40.63, 127.0);

        Group group1 = persistGroup();
        Group group2 = persistGroup();

        persistArticleGroup(article1, group1);
        persistArticleGroup(article1, group2);
        persistArticleGroup(article2, group2);


        // when
        List<ArticleGroup> ArticleGroup1 = articleGroupRepository.findAllByArticleId(article1.getId());
        List<ArticleGroup> ArticleGroup2 = articleGroupRepository.findAllByArticleId(article2.getId());
        List<ArticleGroup> ArticleGroup3 = articleGroupRepository.findAllByArticleId(article3.getId());

        // then
        assertThat(ArticleGroup1.size()).isEqualTo(2);
        assertThat(ArticleGroup2.size()).isEqualTo(1);
        assertThat(ArticleGroup3.size()).isEqualTo(0);
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
