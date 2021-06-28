package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.utils.HashUtil;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    public Long createGroup(CreateGroupRequest createGroupRequest, User creator) {
        Group savedGroup = saveGroup(createGroupRequest.getName(), HashUtil.hash(creator.getId() + ""));

        saveUserGroup(savedGroup, creator);

        return savedGroup.getId();
    }

    public Long join(String invitationCode, User user) {
        Group group = groupRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new IllegalArgumentException(invitationCode + "는 존재하지 않는 초대코드입니다."));

        saveUserGroup(group, user);

        return group.getId();
    }

    private Group saveGroup(String groupName, String invitationCode) {
        Group group = Group.builder()
                .name(groupName)
                .invitationCode(invitationCode)
                .build();
        return groupRepository.save(group);
    }

    private void saveUserGroup(Group group, User user) {
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(user)
                .build();
        userGroupRepository.save(userGroup);
    }
}
