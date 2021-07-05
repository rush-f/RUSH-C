package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistGroup;
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
class ArticleGroupRepositoryTest {

    @Autowired
    private ArticleGroupRepository articleGroupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findAllByArticleId() {
        // given
        User user = persistUser(testEntityManager, "test@email.com");
        Group group = persistGroup(testEntityManager);
        Article article = persistArticle(testEntityManager, user, false, false, 50.5, 50.5);
        ArticleGroup articleGroup = persistArticleGroup(group, article);

        // when
        List<ArticleGroup> articleGroups = articleGroupRepository.findAllByArticleId(article.getId());

        // then
        assertThat(articleGroups.size()).isEqualTo(1);
        assertThat(articleGroups.get(0).getId()).isEqualTo(articleGroup.getId());
        assertThat(articleGroups.get(0).getGroup().getId()).isEqualTo(group.getId());
        assertThat(articleGroups.get(0).getArticle().getId()).isEqualTo(article.getId());
    }

    private ArticleGroup persistArticleGroup(Group group, Article article) {
        ArticleGroup articleGroup = ArticleGroup.builder()
            .article(article)
            .group(group)
            .build();
        return testEntityManager.persist(articleGroup);
    }
}
