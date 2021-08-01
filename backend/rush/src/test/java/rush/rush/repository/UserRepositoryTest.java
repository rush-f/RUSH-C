package rush.rush.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static rush.rush.repository.SetUpMethods.persistGroup;
import static rush.rush.repository.SetUpMethods.persistUser;
import static rush.rush.repository.SetUpMethods.persistUserGroup;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;

class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void findAllByGroupId() {
        // given
        User user1 = persistUser(testEntityManager, "test1@naver.com");
        User user2 = persistUser(testEntityManager, "test2@naver.com");
        User user3 = persistUser(testEntityManager, "test3@naver.com");
        Group group1 = persistGroup(testEntityManager);
        Group group2 = persistGroup(testEntityManager);
        persistUserGroup(testEntityManager, user1, group1);
        persistUserGroup(testEntityManager, user2, group1);
        persistUserGroup(testEntityManager, user3, group2);

        // when
        List<User> group1Members = userRepository.findAllByGroupId(group1.getId());

        // then
        assertThat(group1Members.size()).isEqualTo(2);
    }
}