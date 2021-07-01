package rush.rush.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupTest {

    private static final String TEST_GROUP_NAME = "그룹이름";

    @Test
    void constructor() {
        Group group = Group.builder()
                .name(TEST_GROUP_NAME)
                .build();

        assertThat(group).isNotNull();
    }

    @Test
    void constructor_IfNameIsEmpty_ThrowException() {
        assertThatThrownBy(() -> Group.builder()
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
