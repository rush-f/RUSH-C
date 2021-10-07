package rush.rush.service.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.MyPageArticleResponse;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FindMyArticlesServiceTest {

    @Autowired
    FindMyArticlesService findMyArticlesService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findMyArticles() {
        //given
        User user = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );
        Article article = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(0.0)
                .longitude(0.0)
                .publicMap(true)
                .privateMap(false)
                .build()
        );

        //when
        List<MyPageArticleResponse> articles = findMyArticlesService.findMyArticles(user.getId());

        //then
        assertThat(articles.get(0).getId()).isEqualTo(article.getId());
        assertThat(articles).hasSize(1);

    }
}