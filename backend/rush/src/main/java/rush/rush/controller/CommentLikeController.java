package rush.rush.controller;

import java.util.List;
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
@RequestMapping("/articles/{mapType}/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> changeMyLike(
        @PathVariable("commentId") Long commentId,
        @PathVariable("mapType") String mapType,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @CurrentUser UserPrincipal userPrincipal) {
        commentLikeService
            .changeMyLike(commentId, MapType.from(mapType), hasILiked, userPrincipal.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("/{articleId}/like")
    public ResponseEntity<List<Long>> checkMyLike(
        @PathVariable("articleId") Long articleId,
        @PathVariable("mapType") String mapType,
        @CurrentUser UserPrincipal userPrincipal) {
        List<Long> result = commentLikeService
            .hasILiked(articleId, MapType.from(mapType), userPrincipal.getId());

        return ResponseEntity.ok()
            .body(result);
    }
}
