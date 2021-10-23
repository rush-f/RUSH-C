package rush.rush.controller.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.EditArticleContentRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.article.EditArticleService;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class EditArticleController {

    private final EditArticleService editArticleService;

    @PutMapping("/{articleId}")
    public ResponseEntity<Void> editArticle(@PathVariable("articleId") Long articleId,
        @RequestBody EditArticleContentRequest request,
        @CurrentUser UserPrincipal userPrincipal) {
        editArticleService.editArticleContent(articleId, request.getNewContent(),
            userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }

}
