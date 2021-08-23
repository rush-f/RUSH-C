package rush.rush.controller.article.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.article.comment.DeleteCommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class DeleteCommentController {

    private final DeleteCommentService deleteCommentService;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
            @CurrentUser UserPrincipal userPrincipal) {
        deleteCommentService.deleteComment(commentId, userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }
}
