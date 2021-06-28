package rush.rush.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        User me = persistUser("me@email.com");
        Group group1 = persistGroup();
        Group group2 = persistGroup();

        persistUserGroup(me, group1);
        persistUserGroup(me, group2);

        User another = persistUser("another@email.com");
        Group group3 = persistGroup();
        persistUserGroup(another, group3);

        // when
        List<UserGroup> userGroups = userGroupRepository.findAllByUserId(me.getId());

        // then
        assertThat(userGroups.size()).isEqualTo(2);
    }

    private User persistUser(String email) {
        User user = User.builder()
                .email(email)
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build();
        return testEntityManager.persist(user);
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
