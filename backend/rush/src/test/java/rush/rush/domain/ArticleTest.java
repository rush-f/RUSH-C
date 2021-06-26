package rush.rush.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTest {

    @ParameterizedTest
    @MethodSource("constructorTestParameters")
    void constructor_IfIsEmpty_ExceptionThrown(String title, String content) {
        // given
        User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .password("test password")
//            .invitationCode("test invitation Code")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();

        // when & then
        assertThatThrownBy(() -> new Article(null, title, content, 0, 0, user, null))
            .isInstanceOf(IllegalArgumentException.class);
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
