package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class GroupTest {

    private static final String TEST_GROUP_NAME = "그룹이름";

    @Test
    void constructor() {
        Group group = Group.builder()
                .name(TEST_GROUP_NAME)
                .build();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(TEST_GROUP_NAME);
        assertThat(group.getUserGroups()).isNotNull();
    }

    @Test
    void constructor_IfNameIsEmpty_ThrowException() {
        assertThatThrownBy(() -> Group.builder()
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
