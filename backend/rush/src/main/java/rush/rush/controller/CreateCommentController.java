package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.domain.MapType;
import rush.rush.dto.CommentResponse;
import rush.rush.dto.CreateCommentRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CommentService;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class CreateCommentController {

    private final CommentService commentService;

    @PostMapping("/{mapType}/{articleId}/comments")
    public ResponseEntity<CommentResponse> createOnPublicArticle(
            @PathVariable("mapType") String mapType,
            @PathVariable("articleId") Long articleId,
            @RequestBody CreateCommentRequest createCommentRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        CommentResponse commentResponse =
            commentService.create(articleId, MapType.from(mapType), createCommentRequest, userPrincipal.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentResponse);
    }
}
