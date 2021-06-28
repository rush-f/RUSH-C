package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.utils.RandomStringGenerator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public Long createGroup(CreateGroupRequest createGroupRequest, User creator) {
        Group savedGroup = saveGroup(createGroupRequest.getName());

        String invitationCode = generateInvitationCode(savedGroup.getId());
        savedGroup.setInvitationCode(invitationCode);

        saveUserGroup(savedGroup, creator);

        return savedGroup.getId();
    }

    public Long join(String invitationCode, User user) {
        Group group = groupRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new IllegalArgumentException(invitationCode + "는 존재하지 않는 초대코드입니다."));

        saveUserGroup(group, user);

        return group.getId();
    }

    public List<GroupSummaryResponse> findAllByUser(User user) {
        List<UserGroup> userGroups = userGroupRepository.findAllByUserId(user.getId());

        return userGroups.stream()
                .map(userGroup -> {
                    Group group = userGroup.getGroup();
                    return new GroupSummaryResponse(group.getId(), group.getName());
                })
                .collect(Collectors.toUnmodifiableList());
    }

    private Group saveGroup(String groupName) {
        Group group = Group.builder()
                .name(groupName)
                .build();
        return groupRepository.save(group);
    }

    private String generateInvitationCode(Long groupId) {
        return RandomStringGenerator.generate(5) + groupId;
    }

    private void saveUserGroup(Group group, User user) {
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(user)
                .build();
        userGroupRepository.save(userGroup);
    }
}
