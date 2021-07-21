package rush.rush.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CommentResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CommentService;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class FindCommentController {

    private final CommentService commentService;

    @GetMapping("/public/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> findCommentsOfPublicArticle(
            @PathVariable("articleId") Long articleId) {
        List<CommentResponse> comments = commentService.findCommentsByArticleId(articleId);

        return ResponseEntity.ok()
            .body(comments);
    }

    @GetMapping("/private/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> findCommentsOfPrivateArticle(
            @PathVariable("articleId") Long articleId,
            @CurrentUser UserPrincipal userPrincipal) {
        List<CommentResponse> comments = commentService.findCommentsByArticleId(articleId);

        return ResponseEntity.ok()
            .body(comments);
    }

    @GetMapping("/grouped/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> findCommentsOfGroupedArticle(
            @PathVariable("articleId") Long articleId,
            @CurrentUser UserPrincipal userPrincipal) {
        List<CommentResponse> comments = commentService.findCommentsByArticleId(articleId);

        return ResponseEntity.ok()
            .body(comments);
    }
}
