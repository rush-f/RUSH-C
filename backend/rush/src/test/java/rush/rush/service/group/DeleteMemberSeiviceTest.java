package rush.rush.service.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class DeleteMemberSeiviceTest {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    DeleteMemberService deleteMemberService;

    private Group group;
    private User user;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
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
                .name("테스트 그룹")
                .build()
        );
        userGroup = userGroupRepository.save(
            UserGroup.builder()
                .group(group)
                .user(user)
                .build()
        );
        group.adduserGroup(userGroup); // 주의!! 고아객체 자동 제거를 위해선 반드시 이 과정이 필요함!!!
    }

    @Test
    @Transactional
    @DisplayName("그룹 탈퇴 - 그룹 탈퇴 후 그룹원이 한명이상 남아 있을 경우")
    void deleteMember() {
        //given 다른 그룹원 추가
        User user2 = userRepository.save(
            User.builder()
                .email("user2@test.com")
                .password("2222")
                .nickName("2222")
                .provider(AuthProvider.local)
                .build()
        );
        UserGroup userGroup2 = userGroupRepository.save(
            UserGroup.builder()
                .group(group)
                .user(user2)
                .build()
        );
        group.adduserGroup(userGroup2);

        //when
        deleteMemberService.deleteMember(group.getId(), user.getId());

        //then
        assertThat(userGroupRepository.findAllByGroupId(group.getId()))
            .hasSize(1);
        assertThat(groupRepository.findAll())
            .hasSize(1);
    }

    @Test
    @Transactional
    @DisplayName("그룹 탈퇴 - 그룹 탈퇴 후 그룹원이 한명도 없을 경우")
    void deleteMember_IfNotMember() {
        //when
        deleteMemberService.deleteMember(group.getId(), user.getId());

        //then
        assertThat(userGroupRepository.findAllByGroupId(group.getId()))
            .hasSize(0);
        assertThat(groupRepository.findAll())
            .hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("그룹 탈퇴 - 본인이 아닌데 탈퇴 시도시 예외처리")
    void deleteMember_IfNotUser_ThrowException() {
        assertThatThrownBy(() -> deleteMemberService.deleteMember(group.getId(), 10L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Transactional
    @DisplayName(" 그룹 탈퇴 - 존재하지 않는 그룹 탈퇴 시도시 예외처리")
    void deleteMember_IfNotExistGroup_ThrowException() {
        assertThatThrownBy(() -> deleteMemberService.deleteMember(10L, user.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

