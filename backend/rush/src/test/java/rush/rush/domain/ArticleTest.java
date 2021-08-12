package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArticleTest {

    @Test
    void constructor() {
        User user = makeFakeUser();
        List<ArticleGroup> articleGroups = makeFakeArticleGroups(user);

        Article article = Article.builder()
            .id(2L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(user)
            .privateMap(true)
            .publicMap(true)
            .articleGroups(articleGroups)
            .build();
        assertThat(article).isInstanceOf(Article.class);
        assertThat(article.getArticleGroups()).hasSize(1);
        assertThat(article.getArticleLikes()).isNotNull();
        assertThat(article.getComments()).isNotNull();
    }

    private User makeFakeUser() {
        return User.builder()
            .id(1L)
            .email("test@test.com")
            .password("test password")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();
    }

    private List<ArticleGroup> makeFakeArticleGroups(User user) {
        Group group = Group.builder()
            .id(1L)
            .name("hi")
            .build();
        Article article1 = Article.builder()
            .id(1L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(user)
            .privateMap(true)
            .publicMap(true)
            .build();

        List<ArticleGroup> articleGroupList = new ArrayList<>();

        articleGroupList.add(ArticleGroup.builder()
            .id(1L)
            .article(article1)
            .group(group)
            .build());
        return articleGroupList;
    }

    @ParameterizedTest
    @MethodSource("constructorTestParameters")
    void constructor_IfIsEmpty_ExceptionThrown(String title, String content) {
        // given
        User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .password("test password")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();

        // when & then
        assertThatThrownBy(() -> Article.builder()
            .id(1L)
            .title(title)
            .content(content)
            .latitude(0.0)
            .longitude(0.0)
            .user(user)
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
