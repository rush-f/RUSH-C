package rush.rush.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CommentResponse;
import rush.rush.dto.CreateCommentRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestParam(value = "article_id") Long articleId,
        @RequestBody CreateCommentRequest createCommentRequest,
        @CurrentUser UserPrincipal userPrincipal) {
        CommentResponse commentResponse =
            commentService.create(articleId, createCommentRequest, userPrincipal.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentResponse);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findCommentsByArticleId(
        @RequestParam(value = "article_id") Long articleId) {
        List<CommentResponse> comments = commentService.findCommentsByArticleId(articleId);

        return ResponseEntity.ok()
            .body(comments);
    }
}
