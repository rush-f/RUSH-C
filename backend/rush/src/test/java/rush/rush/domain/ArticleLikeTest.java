package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class ArticleLikeTest {

    @Mock
    private User articleAuthor;

    @Mock
    private User user;

    @Mock
    private Article article;

    @BeforeEach
    void setUp() {
        articleAuthor = Mockito.mock(User.class);
        when(articleAuthor.getId()).thenReturn(1L);

        user = Mockito.mock(User.class);
        when(user.getId()).thenReturn(2L);

        article = Article.builder()
            .id(2L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(articleAuthor)
            .privateMap(true)
            .publicMap(true)
            .build();
    }

    @Test
    @DisplayName("Builder")
    void builder() {
        ArticleLike articleLike = ArticleLike.builder()
            .user(user)
            .article(article)
            .build();

        assertThat(articleLike).isInstanceOf(ArticleLike.class);
        assertThat(articleLike.getUser().getId()).isEqualTo(user.getId());
        assertThat(articleLike.getArticle().getId()).isEqualTo(article.getId());

        // ArticleLike 객체 저장시 Article의 oneToMany 필드에도 추가됨
        assertThat(article.getArticleLikes()).hasSize(1);
        ArticleLike articleLikeOfArticle = article.getArticleLikes().get(0);
        assertThat(articleLikeOfArticle.getId()).isEqualTo(articleLike.getId());
    }
}