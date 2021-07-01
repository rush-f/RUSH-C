package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistUser;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class UserGroupRepositoryTest {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findAllByUserId() {
        // given
        User me = persistUser(testEntityManager, "me@email.com");
        Group group1 = persistGroup();
        Group group2 = persistGroup();

        persistUserGroup(me, group1);
        persistUserGroup(me, group2);

        User another = persistUser(testEntityManager, "another@email.com");
        Group group3 = persistGroup();
        persistUserGroup(another, group3);

        // when
        List<UserGroup> userGroups = userGroupRepository.findAllByUserId(me.getId());

        // then
        assertThat(userGroups.size()).isEqualTo(2);
    }

    @Test
    void findByUserIdAndGroupId() {
        // given
        User user = persistUser(testEntityManager, "user@email.com");
        Group group = persistGroup();
        persistUserGroup(user, group);

        // when
        Optional<UserGroup> userGroup = userGroupRepository.findByUserIdAndGroupId(user.getId(), group.getId());

        // then
        assertThat(userGroup.isPresent()).isTrue();
        assertThat(userGroup.get().getGroup().getId()).isEqualTo(group.getId());
        assertThat(userGroup.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void findByUserIdAndGroupId_IfNotExist_ReturnEmpty() {
        // given
        User user = persistUser(testEntityManager, "user@email.com");
        Group group = persistGroup();

        // when
        Optional<UserGroup> userGroup = userGroupRepository.findByUserIdAndGroupId(user.getId(), group.getId());

        // then
        assertThat(userGroup.isPresent()).isFalse();
    }

    private Group persistGroup() {
        Group group = Group.builder()
                .name(Constants.TEST_GROUP_NAME)
                .build();
        return testEntityManager.persist(group);
    }

    private void persistUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(user)
                .build();
        testEntityManager.persist(userGroup);
    }
}
