package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.domain.MapType;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CommentLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{mapType}/comments/{commentId}/like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<Void> changeMyLike(
        @PathVariable("commentId") Long commentId,
        @PathVariable("mapType") String mapType,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @CurrentUser UserPrincipal userPrincipal){
        commentLikeService.changeMyLike(commentId, MapType.from(mapType), hasILiked, userPrincipal.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @GetMapping
    public ResponseEntity<Boolean> checkMyLike(
        @PathVariable("commentId") Long commentId,
        @PathVariable("mapType") String mapType,
        @CurrentUser UserPrincipal userPrincipal){
        boolean result= commentLikeService.hasILiked(commentId, MapType.from(mapType), userPrincipal.getId());

        return ResponseEntity.ok()
            .body(result);
    }
}
