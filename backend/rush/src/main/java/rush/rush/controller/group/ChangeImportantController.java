package rush.rush.controller.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.group.ChangeImportantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChangeImportantController {

    private final ChangeImportantService changeImportantService;

    @PutMapping("/{groupId}/important")
    public ResponseEntity<Void> changeImportant(@PathVariable("groupId") Long groupId,
            @CurrentUser UserPrincipal userPrincipal) {
        changeImportantService.changeImportant(groupId, userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }
}
