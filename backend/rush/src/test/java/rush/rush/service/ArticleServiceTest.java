package rush.rush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rush.rush.dto.CreateArticleRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void create() {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("af", "sdf", 0, 0);
        assertThat(articleService.create(createArticleRequest)).isNotNull();
    }
}