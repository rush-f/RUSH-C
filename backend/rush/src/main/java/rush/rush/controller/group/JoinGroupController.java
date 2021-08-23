package rush.rush.controller.group;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.group.JoinGroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class JoinGroupController {

    private final JoinGroupService joinGroupService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestParam(value = "invitation_code") String invitationCode, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = joinGroupService.join(invitationCode, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/api/groups/" + groupId))
                .build();
    }
}
