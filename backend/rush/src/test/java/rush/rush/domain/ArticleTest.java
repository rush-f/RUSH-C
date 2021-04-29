package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArticleTest {

    @ParameterizedTest
    @MethodSource("constructorTestParameters")
    void constructor_IfIsEmpty_ExceptionThrown(String title, String content) {
        assertThatThrownBy(() -> new Article(null, title, content, null))
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