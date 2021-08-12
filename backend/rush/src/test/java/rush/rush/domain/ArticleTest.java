package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArticleTest {

    @Test
    @DisplayName("생성자 - List 필드 주입 하지 않을 때")
    void constructor() {
        User user = makeFakeUser(1L, "test1@email.com", "password", "닉네임", AuthProvider.local);

        Article article = Article.builder()
            .id(2L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(user)
            .privateMap(true)
            .publicMap(true)
            .build();

        assertThat(article).isInstanceOf(Article.class);

        assertThat(article.getArticleGroups()).isNotNull();
        assertThat(article.getArticleGroups()).hasSize(0);

        assertThat(article.getArticleLikes()).isNotNull();
        assertThat(article.getArticleLikes()).hasSize(0);

        assertThat(article.getComments()).isNotNull();
        assertThat(article.getComments()).hasSize(0);
    }

    @Test
    @DisplayName("생성자 - List 필드 주입")
    void constructor_withLstFieldInjection() {
        User user = makeFakeUser(1L, "test1@email.com", "password", "닉네임", AuthProvider.local);
        List<ArticleGroup> articleGroups = makeFakeArticleGroups();
        List<ArticleLike> articleLikes = makeFakeArticleLikes();
        List<Comment> comments = makeFakeComments();

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
            .articleLikes(articleLikes)
            .comments(comments)
            .build();

        assertThat(article).isInstanceOf(Article.class);

        assertThat(article.getArticleGroups()).hasSize(1);
        assertThat(article.getArticleLikes()).hasSize(1);
        assertThat(article.getComments()).hasSize(1);
    }

    private User makeFakeUser(Long id, String email, String password, String nickname, AuthProvider authProvider) {
        return User.builder()
            .id(id)
            .email(email)
            .password(password)
            .nickName(nickname)
            .provider(authProvider)
            .build();
    }

    private List<ArticleGroup> makeFakeArticleGroups() {
        User user = makeFakeUser(1000L, "test1000@test.com", "password", "test", AuthProvider.local);
        Group group = Group.builder()
            .id(1L)
            .name("hi")
            .build();
        Article article = Article.builder()
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
            .article(article)
            .group(group)
            .build());
        return articleGroupList;
    }

    private List<ArticleLike> makeFakeArticleLikes() {
        User user = makeFakeUser(1000L, "test1000@test.com", "password", "test", AuthProvider.local);
        Article article = makeFakeArticle(user);

        List<ArticleLike> articleLikes = new ArrayList<>();

        articleLikes.add(ArticleLike.builder()
            .id(1L)
            .article(article)
            .user(user)
            .build());
        return articleLikes;
    }

    private List<Comment> makeFakeComments() {
        User user = makeFakeUser(1000L, "test1000@test.com", "password", "test", AuthProvider.local);
        Article article = makeFakeArticle(user);

        List<Comment> comments = new ArrayList<>();

        comments.add(new Comment(1L, "댓글내용", user, article, null));

        return comments;
    }

    private Article makeFakeArticle(User fakeArticleAuthor) {
        return Article.builder()
            .id(1L)
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(fakeArticleAuthor)
            .privateMap(true)
            .publicMap(true)
            .build();
    }

    @ParameterizedTest
    @MethodSource("constructorTestParameters")
    @DisplayName("생성자 - 제목이나 내용이 비어있을 때 예외처리")
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
