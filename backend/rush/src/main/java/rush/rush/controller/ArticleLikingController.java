package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.ArticleLikingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like/articles")
public class ArticleLikingController {

    private final ArticleLikingService articleLikingService;

    @PostMapping
    public ResponseEntity<Void> addArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        articleLikingService.addArticleLiking(articleId, userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        articleLikingService.deleteArticleLiking(articleId, userPrincipal.getId());

        return ResponseEntity.noContent()
            .build();
    }

    @GetMapping
    public ResponseEntity<Boolean> checkArticleMyLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        boolean result= articleLikingService.checkArticleMyLiking(articleId, userPrincipal.getId());

        return ResponseEntity.ok()
            .body(result);
    }
}
