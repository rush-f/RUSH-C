package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistArticle;
import static rush.rush.repository.SetUpMethods.persistArticleGroup;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.GroupResponse;

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
        Optional<GroupResponse> result = groupRepository
            .findGroupDetail(group.getId(), user.getId());

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

    @Test
    @Transactional
    void deleteById(@Autowired ArticleGroupRepository articleGroupRepository) {
        //given user, article 각각 그룹에 포함하도록 작성
        User user = persistUser(testEntityManager, "test1@email.com");
        Group group = persistGroup(testEntityManager);
        Article article = persistArticle(testEntityManager, user, true, true, 0.0, 0.0);
        ArticleGroup articleGroup = persistArticleGroup(testEntityManager, article, group);
        group.addArticleGroup(articleGroup); // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!

        // then 삭제 전
        assertThat(groupRepository.findAll()).hasSize(1);
        assertThat(articleGroupRepository.findAll()).hasSize(1);

        // when
        groupRepository.deleteById(group.getId());

        // then 삭제 후
        assertThat(groupRepository.findAll()).hasSize(0);
        assertThat(articleGroupRepository.findAll()).hasSize(0);
    }
}
