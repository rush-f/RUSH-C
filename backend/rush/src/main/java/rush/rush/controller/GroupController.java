package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.GroupService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> create(CreateGroupRequest createGroupRequest, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = groupService.createGroup(createGroupRequest, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/groups/" + groupId))
                .build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestParam(value = "invitation_code") String invitationCode, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = groupService.join(invitationCode, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/groups/" + groupId))
                .build();
    }

    @GetMapping("/mine")
    public ResponseEntity<List<GroupSummaryResponse>> findMyGroups(@CurrentUser UserPrincipal userPrincipal) {
        List<GroupSummaryResponse> groupSummaryResponses = groupService.findAllByUser(userPrincipal.getUser());

        return ResponseEntity.ok()
                .body(groupSummaryResponses);
    }
}
