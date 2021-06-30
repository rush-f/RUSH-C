package rush.rush.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.FindMapArticlesRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.FindArticleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class FindArticleController {

    private final FindArticleService findArticleService;

    @GetMapping("/public")
    public ResponseEntity<List<ArticleSummaryResponse>> findPublicMapArticles(
            @RequestParam FindMapArticlesRequest findMapArticlesRequest) {
        List<ArticleSummaryResponse> publicMapArticles = findArticleService.findPublicMapArticles(
            findMapArticlesRequest.getLatitude(), findMapArticlesRequest.getLatitudeRange(),
            findMapArticlesRequest.getLongitude(), findMapArticlesRequest.getLongitudeRange()
        );
        return ResponseEntity.ok()
            .body(publicMapArticles);
    }

    @GetMapping("/private")
    public ResponseEntity<List<ArticleSummaryResponse>> findPrivateMapArticles(
        @RequestParam FindMapArticlesRequest findMapArticlesRequest,
        @CurrentUser UserPrincipal userPrincipal) {
        List<ArticleSummaryResponse> privateMapArticles = findArticleService.findPrivateMapArticles(
            findMapArticlesRequest.getLatitude(), findMapArticlesRequest.getLatitudeRange(),
            findMapArticlesRequest.getLongitude(), findMapArticlesRequest.getLongitudeRange(),
            userPrincipal.getUser()
        );
        return ResponseEntity.ok()
            .body(privateMapArticles);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ArticleResponse> findPublicArticle(@PathVariable Long id) {
        ArticleResponse articleResponse = findArticleService.findPublicArticle(id);

        return ResponseEntity.ok()
            .body(articleResponse);
    }
}
