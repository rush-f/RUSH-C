package rush.rush.service.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteArticleServiceTest extends ServiceTest {

    @Autowired
    DeleteArticleService deleteArticleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    private User user;
    private Article article;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );
        article = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(0.0)
                .longitude(0.0)
                .publicMap(true)
                .privateMap(true)
                .build()
        );
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteArticle() {
        //when
        deleteArticleService.deleteArticle(article.getId(), user);

        //then
        assertThat(articleRepository.findAllByUserId(user.getId())).hasSize(0);
    }

    @Test
    @DisplayName("게시글 삭제 - 해당되는 게시글이 없는 경우")
    void deleteArticle_IfNotExistsArticle() {
        //when & then
        assertThatThrownBy(() -> deleteArticleService.deleteArticle(100L, user))
            .isInstanceOf(NotArticleExistsException.class);
    }

    @Test
    @DisplayName("게시글 삭제 - 글 작성자가 아닌 경우")
    void deleteArticle_IfNotAuthorized() {
        //given
        User another = userRepository.save(
            User.builder()
                .email("test2@test.com")
                .password("test password")
                .nickName("test2")
                .provider(AuthProvider.local)
                .build()
        );

        //when & then
        assertThatThrownBy(() -> deleteArticleService.deleteArticle(article.getId(), another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}
