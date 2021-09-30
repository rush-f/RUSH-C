package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rush.rush.exception.WrongInputException;

class GroupBuilderTest {

    private static final String TEST_GROUP_NAME = "그룹이름";

    @Test
    @DisplayName("Builder")
    void builder() {
        Group group = Group.builder()
                .name(TEST_GROUP_NAME)
                .build();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(TEST_GROUP_NAME);
        assertThat(group.getUserGroups()).isNotNull();
    }

    @Test
    @DisplayName("Builder - 그룹이름이 비어있을 경우 예외처리")
    void builder_IfNameIsEmpty_ThrowException() {
        assertThatThrownBy(() -> Group.builder()
                .build()
        ).isInstanceOf(WrongInputException.class);
    }
}
