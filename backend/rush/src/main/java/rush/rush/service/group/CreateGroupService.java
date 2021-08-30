package rush.rush.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.utils.RandomStringGenerator;

@Service
@RequiredArgsConstructor
public class CreateGroupService {

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
