package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.repository.GroupRepository;
import rush.rush.utils.HashUtil;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Long createGroup(CreateGroupRequest createGroupRequest, User user) {
        Group group = Group.builder()
                .name(createGroupRequest.getName())
                .invitationCode(HashUtil.hash(user.getId() + ""))
                .build();

        return groupRepository.save(group)
                .getId();
    }
}
