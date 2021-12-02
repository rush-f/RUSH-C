package rush.rush.service.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.GroupResponse;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class EditGroupNameServiceTest extends ServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private FindGroupService findGroupService;

    @Autowired
    private EditGroupNameService editGroupNameService;

    private User user;
    private Group group;

    @BeforeEach
    void setUp(){
        user = userRepository.save(
            User.builder()
                .email("user@test.com")
                .password("1111")
                .nickName("1111")
                .provider(AuthProvider.local)
                .build()
        );
        group = groupRepository.save(
            Group.builder()
                .name("원래 그룹")
                .build()
        );
        userGroupRepository.save(
            UserGroup.builder()
                .group(group)
                .user(user)
                .build()
        );
    }

    @Test
    void editGroupName() {
        // given
        String newName = "변경된 그룹이름";

        // when
        editGroupNameService.editGroupName(group.getId(), newName, user);

        // then
        GroupResponse groupResponse = findGroupService.findOne(group.getId(), user);
        assertThat(groupResponse.getName()).isEqualTo(newName);
    }
}
