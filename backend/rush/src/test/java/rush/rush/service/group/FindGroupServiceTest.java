package rush.rush.service.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.GroupResponse;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.dto.SimpleUserResponse;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FindGroupServiceTest {

    @Autowired
    FindGroupService findGroupService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

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
    @DisplayName("그룹리스트찾기")
    void findAllByUser() {
        //when
        List<GroupSummaryResponse> responses = findGroupService.findAllByUser(user);

        //then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getId()).isEqualTo(group.getId());
    }

    @Test
    @Transactional
    @DisplayName("즐겨찾기 그룹리스트찾기")
    void findImportantGroupsByUser() {
        //when
        List<GroupSummaryResponse> responses = findGroupService.findImportantGroupsByUser(user);

        //then
        assertThat(responses).hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("그룹찾기")
    void findOne() {
        //when
        GroupResponse groupResponse = findGroupService.findOne(group.getId(), user);

        //then
        assertThat(groupResponse.getId()).isEqualTo(group.getId());

        //given   그룹에 속하지 않은 다른 유저
        User another = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("1111")
                .nickName("1111")
                .provider(AuthProvider.local)
                .build()
        );

        //when & then  다른 유저가 접근할 경우
        assertThatThrownBy(() ->  findGroupService.findOne(group.getId(), another))
            .isInstanceOf(NotAuthorizedOrExistException.class);

        //when & then  해당 그룹이 없는 경우
        assertThatThrownBy(() ->  findGroupService.findOne(100L, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("그룹원찾기")
    void findMembers() {
        //when
        List<SimpleUserResponse> responses = findGroupService.findMembers(group.getId(), user);

        //then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getId()).isEqualTo(user.getId());

        //given   그룹에 속하지 않은 다른 유저
        User another = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("1111")
                .nickName("1111")
                .provider(AuthProvider.local)
                .build()
        );

        //when & then  다른 유저가 접근할 경우
        assertThatThrownBy(() ->  findGroupService.findMembers(group.getId(), another))
            .isInstanceOf(NotAuthorizedOrExistException.class);

        //when & then  해당 그룹이 없는 경우
        assertThatThrownBy(() ->  findGroupService.findMembers(100L, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}