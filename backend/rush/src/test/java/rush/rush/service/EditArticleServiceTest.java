package rush.rush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.article.EditArticleService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class EditArticleServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    EditArticleService editArticleService;

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
                .privateMap(false)
                .build()
        );
    }

    @Test
    @Transactional
    @DisplayName("글내용수정")
    void editArticleContent() {
        //given
        String newContent = "새로운 내용이다!";

        //when
        editArticleService.editArticleContent(article.getId(), newContent, user);
        entityManager.clear();

        //then
        assertThat(articleRepository.getOne(article.getId()).getContent())
            .isEqualTo(newContent);
    }

    @Test
    @Transactional
    @DisplayName("글내용수정 - 해당되는 게시글이 없는 경우")
    void deleteArticle_IfNotExistsArticle() {
        //given
        String newContent = "새로운 내용이다!";

        //when & then
        assertThatThrownBy(
            () -> editArticleService.editArticleContent(100L, newContent, user))
            .isInstanceOf(NotArticleExistsException.class);
    }

    @Test
    @Transactional
    @DisplayName("글내용수정 - 글 작성자가 아닌 경우")
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
        String newContent = "새로운 내용이다!";

        //when & then
        assertThatThrownBy(
            () -> editArticleService.editArticleContent(article.getId(), newContent, another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}