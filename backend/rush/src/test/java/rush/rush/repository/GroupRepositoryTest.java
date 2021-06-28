package rush.rush.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.utils.HashUtil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class GroupRepositoryTest {

    private static final String TEST_GROUP_NAME = "테스트 그룹이름";
    private static final String TEST_INVITATION_CODE = HashUtil.hash("1");

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    void findByInvitationCode() {
        // given
        Group group = createGroup();

        // when
        Optional<Group> result = groupRepository.findByInvitationCode(TEST_INVITATION_CODE);

        // then
        assertThat(result.isPresent()).isTrue();

        Group resultGroup = result.get();

        assertThat(resultGroup.getId()).isEqualTo(group.getId());
        assertThat(resultGroup.getName()).isEqualTo(group.getName());
        assertThat(resultGroup.getInvitationCode()).isEqualTo(group.getInvitationCode());
        assertThat(resultGroup.getCreateDate()).isEqualTo(group.getCreateDate());
    }

    private Group createGroup() {
        Group group = Group.builder()
                .name(TEST_GROUP_NAME)
                .invitationCode(TEST_INVITATION_CODE)
                .build();
        return testEntityManager.persist(group);
    }
}