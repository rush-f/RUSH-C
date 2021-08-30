package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;

class GroupRepositoryTest extends RepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @Transactional
    void findByInvitationCode() {
        // given
        Group group = persistGroup(testEntityManager);

        // when
        Optional<Group> result = groupRepository.findByInvitationCode(group.getInvitationCode());

        // then
        assertThat(result.isPresent()).isTrue();

        Group resultGroup = result.get();

        assertThat(resultGroup.getId()).isEqualTo(group.getId());
        assertThat(resultGroup.getName()).isEqualTo(group.getName());
        assertThat(resultGroup.getInvitationCode()).isNotNull();
        assertThat(resultGroup.getInvitationCode()).isEqualTo(group.getInvitationCode());
        assertThat(resultGroup.getCreateDate()).isEqualTo(group.getCreateDate());
    }

    @Test
    @Transactional
    void findByGroupIdAndUserId() {
        // given
        User user = persistUser(testEntityManager, "test@seoultech.com");
        Group group = persistGroup(testEntityManager);
        persistUserGroup(testEntityManager, user, group);

        // when
        Optional<Group> result = groupRepository
            .findByGroupIdAndUserId(group.getId(), user.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(group.getId());
    }

    @Test
    @Transactional
    void findAllByUserId() {
        // given
        User user1 = persistUser(testEntityManager, "test@seoultech.com");
        Group group1 = persistGroup(testEntityManager);
        Group group2 = persistGroup(testEntityManager);
        persistUserGroup(testEntityManager, user1, group1);
        persistUserGroup(testEntityManager, user1, group2);

        // when
        List<Group> groups = groupRepository.findAllByUserId(user1.getId());

        // then
        assertThat(groups.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("그룹 이름 바꾸기")
    void editGroupName() {
        // given
        User user = persistUser(testEntityManager, "test@seoultech.com");
        Group group = persistGroup(testEntityManager);
        persistUserGroup(testEntityManager, user, group);

        // when
        String newGroupName = "new group name";
        groupRepository.editGroupName(group.getId(), newGroupName);
        testEntityManager.clear();

        // then
        Group result = testEntityManager.find(Group.class, group.getId());
        assertThat(result.getName()).isEqualTo(newGroupName);
    }
}
