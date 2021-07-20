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
@RequestMapping("/liking/article")
public class ArticleLikingController {

    private final ArticleLikingService articleLikingService;

    @PostMapping("/add")
    public ResponseEntity<Void> AddArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        articleLikingService.addArticleLiking(articleId, userPrincipal.getId());

        return ResponseEntity.noContent()
            .build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> DeleteArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        articleLikingService.deleteArticleLiking(articleId, userPrincipal.getId());

        return ResponseEntity.noContent()
            .build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> CheckArticleMyLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        boolean result= articleLikingService.checkArticleMyLiking(articleId, userPrincipal.getId());

        return ResponseEntity.ok()
            .body(result);
    }
}
