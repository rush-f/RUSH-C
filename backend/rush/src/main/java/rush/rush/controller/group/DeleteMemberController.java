package rush.rush.controller.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.group.DeleteMemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class DeleteMemberController {

    private final DeleteMemberService deleteMemberService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long groupId,
        @CurrentUser UserPrincipal userPrincipal) {
        deleteMemberService.deleteMember(groupId, userPrincipal.getUser().getId());

        return ResponseEntity.noContent()
            .build();
    }
}
