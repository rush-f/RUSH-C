package rush.rush.environment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProfileTest {

    @Test
    void from() {
        assertThat(Profile.from("LOCAL")).isEqualTo(Profile.LOCAL);
        assertThat(Profile.from("REAL1")).isEqualTo(Profile.REAL1);
        assertThat(Profile.from("REAL2")).isEqualTo(Profile.REAL2);
    }

    @Test
    void port() {
        assertThat(Profile.LOCAL.port()).isEqualTo(8080);
        assertThat(Profile.REAL1.port()).isEqualTo(8081);
        assertThat(Profile.REAL2.port()).isEqualTo(8082);
    }

    @Test
    void path() {
        assertThat(Profile.LOCAL.path()).isEqualTo("classpath:");
        assertThat(Profile.REAL1.path()).isEqualTo("/app/config");
        assertThat(Profile.REAL2.path()).isEqualTo("/app/config");
    }
}