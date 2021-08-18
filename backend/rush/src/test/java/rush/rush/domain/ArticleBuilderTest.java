package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

class ArticleBuilderTest {

    @Mock
    private User articleAuthor;

    @Mock
    private ArticleGroup articleGroup;

    @Mock
    private ArticleLike articleLike;

    @Mock
    private Comment comment;

    @BeforeEach
    void setUp() {
        articleAuthor = Mockito.mock(User.class);
        when(articleAuthor.getId()).thenReturn(1L);

        articleGroup = Mockito.mock(ArticleGroup.class);
        when(articleGroup.getId()).thenReturn(1L);

        articleLike = Mockito.mock(ArticleLike.class);
        when(articleLike.getId()).thenReturn(1L);

        comment = Mockito.mock(Comment.class);
        when(comment.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("Builder - Collection 필드를 주입하지 않을 경우 빈 컬렉션을 가짐")
    void builder_IfNotInjectCollectionFields_EmptyCollection() {
        Article article = Article.builder()
            .id(2L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(articleAuthor)
            .privateMap(true)
            .publicMap(true)
            .build();

        assertThat(article).isInstanceOf(Article.class);

        assertThat(article.getArticleGroups()).isNotNull();    // 컬렉션 필드가 null이 아님
        assertThat(article.getArticleGroups()).hasSize(0);

        assertThat(article.getArticleLikes()).isNotNull();
        assertThat(article.getArticleLikes()).hasSize(0);

        assertThat(article.getComments()).isNotNull();
        assertThat(article.getComments()).hasSize(0);
    }

    @Test
    @DisplayName("Builder - Collection 필드를 주입할 경우")
    void builder_withCollectionFieldInjection() {
        // given : 그룹 1개, 좋아요 1개, 댓글 1개
        List<ArticleGroup> articleGroups = new ArrayList<>();
        articleGroups.add(articleGroup);

        List<ArticleLike> articleLikes = new ArrayList<>();
        articleLikes.add(articleLike);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        // when : article 객체 생성
        Article article = Article.builder()
            .id(2L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(articleAuthor)
            .privateMap(true)
            .publicMap(true)
            .articleGroups(articleGroups)    // List 필드
            .articleLikes(articleLikes)    // List 필드
            .comments(comments)
            .build();

        // then
        assertThat(article).isInstanceOf(Article.class);

        assertThat(article.getArticleGroups()).hasSize(1);
        assertThat(article.getArticleLikes()).hasSize(1);
        assertThat(article.getComments()).hasSize(1);
    }

    @ParameterizedTest
    @MethodSource("constructorTestParameters")
    @DisplayName("Builder - 제목이나 내용이 비어있을 때 예외처리")
    void builder_IfIsEmpty_ExceptionThrown(String title, String content) {
        // when & then
        assertThatThrownBy(() -> Article.builder()
            .id(1L)
            .title(title)
            .content(content)
            .latitude(0.0)
            .longitude(0.0)
            .user(articleAuthor)
            .privateMap(true)
            .publicMap(true)
            .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> constructorTestParameters() {
        return Stream.of(
            Arguments.of(null, null),
            Arguments.of("", null),
            Arguments.of(null, ""),
            Arguments.of("", ""),
            Arguments.of("      ", "     ")
        );
    }
}
