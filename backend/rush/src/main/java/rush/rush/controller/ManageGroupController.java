package rush.rush.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CreateGroupService;
import rush.rush.service.GroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class ManageGroupController {

    private final GroupService groupService;
    private final CreateGroupService createGroupService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateGroupRequest createGroupRequest, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = createGroupService.createGroup(createGroupRequest, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/api/groups/" + groupId))
                .build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestParam(value = "invitation_code") String invitationCode, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = groupService.join(invitationCode, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/api/groups/" + groupId))
                .build();
    }
}
