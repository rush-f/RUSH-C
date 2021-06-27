package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.service.GroupService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class GroupController {

    private final GroupService groupService;

    public ResponseEntity<Void> createGroup(CreateGroupRequest createGroupRequest) {
        Long groupId = groupService.createGroup(createGroupRequest);

        return ResponseEntity.created(URI.create("/groups/" + groupId))
                .build();
    }
}
