package rush.rush.service.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.exception.NotUserExistsException;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ChangeImportantServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    ChangeImportantService changeImportantService;

    @Test
    @Transactional
    @DisplayName("그룹즐겨찾기변경")
    void changeImportant() {
        //given
        User user = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );
        Group group = groupRepository.save(
            Group.builder()
                .name("test")
                .build()
        );
        userGroupRepository.save(
            UserGroup.builder()
                .group(group)
                .user(user)
                .important(false)
                .build()
        );

        //when
        changeImportantService.changeImportant(group.getId(), user);

        //then
        assertThat(userGroupRepository.findAll().get(0).getImportant()).isEqualTo(true);

        //given  그룹에 포함되지 않은 다른 유저
        User another = userRepository.save(
            User.builder()
                .email("test2@test.com")
                .password("test2 password")
                .nickName("test2")
                .provider(AuthProvider.local)
                .build()
        );
        //when & then  user나 group가 userGroup에 없는 경우
        assertThatThrownBy(() ->  changeImportantService.changeImportant(group.getId(), another))
            .isInstanceOf(NotUserExistsException.class);
        assertThatThrownBy(() ->  changeImportantService.changeImportant(10L, user))
            .isInstanceOf(NotUserExistsException.class);
    }
}