package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import rush.rush.exception.WrongInputException;

class ArticleGroupBuilderTest {

    @Mock
    private Article article;

    @Mock
    private Group group;

    @BeforeEach
    void setUp() {
        article = Mockito.mock(Article.class);
        when(article.getId()).thenReturn(1L);

        group = Mockito.mock(Group.class);
        when(group.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("Builder - 필수 파라미터만 전달")
    void builder_OnlyWithNecessaryParams() {
        ArticleGroup articleGroup = ArticleGroup.builder()
            .article(article)
            .group(group)
            .build();

        assertThat(articleGroup.getId()).isNull();
        assertThat(articleGroup.getArticle().getId()).isEqualTo(article.getId());
        assertThat(articleGroup.getGroup().getId()).isEqualTo(group.getId());
        assertThat(articleGroup.getCreateDate()).isNull();
    }

    @Test
    @DisplayName("Builder - 모든 파라미터 전달")
    void builder_WithAllParamsPassed() {
        ArticleGroup articleGroup = ArticleGroup.builder()
            .id(1L)
            .article(article)
            .group(group)
            .createDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();

        assertThat(articleGroup.getId()).isEqualTo(1L);
        assertThat(articleGroup.getArticle().getId()).isEqualTo(article.getId());
        assertThat(articleGroup.getGroup().getId()).isEqualTo(group.getId());
        assertThat(articleGroup.getCreateDate()).isNotNull();
    }

    @Test
    @DisplayName("Builder - article이 null일 때 예외처리")
    void builder_IfArticleIsNull_ThrowException() {
        assertThatThrownBy(() -> ArticleGroup.builder()
            .group(group)
            .build()
        ).isInstanceOf(WrongInputException.class);
    }

    @Test
    @DisplayName("Builder - group이 null일 때 예외처리")
    void builder_IfGroupIsNull_ThrowException() {
        assertThatThrownBy(() -> ArticleGroup.builder()
            .article(article)
            .build()
        ).isInstanceOf(WrongInputException.class);
    }
}
