package rush.rush.service.group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.exception.AlreadySignedUpException;
import rush.rush.exception.NotInvitationCodeExistsException;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JoinGroupServiceTest extends ServiceTest {

    @Autowired
    JoinGroupService joinGroupService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Test
    @DisplayName("그룹가입")
    void join() {
        //given
        User user = userRepository.save(
            User.builder()
                .email("user@test.com")
                .password("1111")
                .nickName("1111")
                .provider(AuthProvider.local)
                .build()
        );
        Group group = groupRepository.save(
            Group.builder()
                .name("원래 그룹")
                .build()
        );

        //when
        joinGroupService.join(group.getInvitationCode(), user);

        //then
        assertThat(userGroupRepository.findAll().get(0).getUser().getId()).isEqualTo(user.getId());

        //when & then  이미 가입된 그룹입 경우
        assertThatThrownBy(() -> joinGroupService.join(group.getInvitationCode(), user))
            .isInstanceOf(AlreadySignedUpException.class);

        //when & then  초대코드가 잘못된 경우
        assertThatThrownBy(() -> joinGroupService.join("아무 테스트 코드", user))
            .isInstanceOf(NotInvitationCodeExistsException.class);
    }
}
