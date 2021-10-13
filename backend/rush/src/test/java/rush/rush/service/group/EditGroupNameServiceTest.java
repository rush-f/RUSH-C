package rush.rush.service.group;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class EditGroupNameServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    EditGroupNameService editGroupNameService;

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
    @Transactional
    void editGroupName() {
        //given
        String newName = "변경된 그룹이름";

        //when
        editGroupNameService.editGroupName(group.getId(), newName, user);
        entityManager.clear();
        //then
        assertThat(groupRepository.getOne(group.getId()).getName())
            .isEqualTo(newName);
    }
}