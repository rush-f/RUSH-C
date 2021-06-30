package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistGroup;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;

@ExtendWith(SpringExtension.class)  // junit5에게 Spring support를 enable 하라고 말하는거
@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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
}
