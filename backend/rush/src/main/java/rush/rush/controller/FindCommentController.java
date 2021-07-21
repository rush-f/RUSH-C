package rush.rush.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.CommentResponse;
import rush.rush.service.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class FindCommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findCommentsByArticleId(
        @RequestParam(value = "article_id") Long articleId) {
        List<CommentResponse> comments = commentService.findCommentsByArticleId(articleId);

        return ResponseEntity.ok()
            .body(comments);
    }
}
