package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.GroupService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> createGroup(CreateGroupRequest createGroupRequest, @CurrentUser UserPrincipal userPrincipal) {
        Long groupId = groupService.createGroup(createGroupRequest, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/groups/" + groupId))
                .build();
    }
}
