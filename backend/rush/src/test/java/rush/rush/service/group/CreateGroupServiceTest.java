package rush.rush.service.group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class CreateGroupServiceTest extends ServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    CreateGroupService createGroupService;

    @Test
    @DisplayName("그룹생성")
    void createGroup() {
        //given
        User user = userRepository.save(
            User.builder()
                .email("user@test.com")
                .password("1111")
                .nickName("1111")
                .provider(AuthProvider.local)
                .build()
        );
        CreateGroupRequest request = new CreateGroupRequest("그룹 이름");

        //when
        Long groupId = createGroupService.createGroup(request, user);

        //then
        assertThat(groupRepository.findAll()).hasSize(1);
        assertThat(userGroupRepository.findByUserIdAndGroupId(user.getId(), groupId)).isPresent();
    }
}
