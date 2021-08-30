package rush.rush.controller.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.EditGroupNameRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.group.EditGroupNameService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class EditGroupNameController {

    private final EditGroupNameService editGroupNameService;

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> editGroupName(@PathVariable("groupId") Long groupId,
            @RequestBody EditGroupNameRequest request,
            @CurrentUser UserPrincipal userPrincipal) {
        editGroupNameService.editGroupName(groupId, request.getNewName(), userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }
}
