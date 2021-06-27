package rush.rush.domain;

import org.junit.jupiter.api.Test;
import rush.rush.utils.HashUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupTest {

    private static final String TEST_GROUP_NAME = "그룹이름";
    private static final String TEST_INVITATION_CODE = HashUtil.hash("그룹 만드는 사람 정보");

    @Test
    void constructor() {
        Group group = Group.builder()
                .name(TEST_GROUP_NAME)
                .invitationCode(TEST_INVITATION_CODE)
                .build();

        assertThat(group).isNotNull();
    }

    @Test
    void constructor_IfNameIsEmpty_ThrowException() {
        assertThatThrownBy(() -> Group.builder()
                .invitationCode(TEST_INVITATION_CODE)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_IfInvitationCodeIsEmpty_ThrowException() {
        assertThatThrownBy(() -> Group.builder()
                .name(TEST_GROUP_NAME)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}