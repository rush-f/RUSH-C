package rush.rush.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.dto.GroupResponse;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.dto.SimpleUserResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.GroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateGroupRequest createGroupRequest, @CurrentUser UserPrincipal userPrincipal) {
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

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findOne(@PathVariable("id") Long groupId, @CurrentUser UserPrincipal userPrincipal) {
        GroupResponse groupResponse = groupService.findOne(groupId, userPrincipal.getUser());

        return ResponseEntity.ok()
                .body(groupResponse);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<SimpleUserResponse>> findMembers(@PathVariable("id") Long groupId, @CurrentUser UserPrincipal userPrincipal) {
        List<SimpleUserResponse> userResponses = groupService.findMembers(groupId, userPrincipal.getUser());

        return ResponseEntity.ok()
            .body(userResponses);
    }
}
