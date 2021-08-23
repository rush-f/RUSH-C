package rush.rush.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class EditGroupNameService {

    private final GroupRepository groupRepository;

    @Transactional
    public void editGroupName(String newGroupName, User user) {
    }
}
