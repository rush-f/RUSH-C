package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.DeleteArticleService;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class DeleteArticleController {

    private final DeleteArticleService deleteArticleService;

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("articleId") Long articleId,
            @CurrentUser UserPrincipal userPrincipal) {
        deleteArticleService.deleteArticle(articleId, userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }
}
