package rush.rush.controller.group;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.GroupResponse;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.dto.SimpleUserResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.group.FindGroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class FindGroupController {

    private final FindGroupService groupService;

    @GetMapping("/mine/important")
    public ResponseEntity<List<GroupSummaryResponse>> findMyImportantGroups(@CurrentUser UserPrincipal userPrincipal) {
        List<GroupSummaryResponse> groupSummaryResponses = groupService.findImportantGroupsByUser(
            userPrincipal.getUser());

        return ResponseEntity.ok()
            .body(groupSummaryResponses);
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
