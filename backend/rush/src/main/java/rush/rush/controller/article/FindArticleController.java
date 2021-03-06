package rush.rush.controller.article;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.ArticleRangeRequest;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.MyPageArticleResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.article.FindArticleService;
import rush.rush.service.article.FindMyArticlesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class FindArticleController {

    private final FindArticleService findArticleService;
    private final FindMyArticlesService findMyArticlesService;

    @GetMapping("/public")
    public ResponseEntity<List<ArticleSummaryResponse>> findPublicMapArticles(ArticleRangeRequest request) {
        List<ArticleSummaryResponse> publicMapArticles = findArticleService.findPublicMapArticles(
            request.getLatitude(), request.getLatitudeRange(),request.getLongitude(), request.getLongitudeRange()
        );
        return ResponseEntity.ok()
            .body(publicMapArticles);
    }

    @GetMapping("/private")
    public ResponseEntity<List<ArticleSummaryResponse>> findPrivateMapArticles(
            ArticleRangeRequest request, @CurrentUser UserPrincipal userPrincipal) {
        List<ArticleSummaryResponse> privateMapArticles = findArticleService.findPrivateMapArticles(
            request.getLatitude(), request.getLatitudeRange(),request.getLongitude(), request.getLongitudeRange(), userPrincipal.getUser());
        return ResponseEntity.ok()
            .body(privateMapArticles);
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<ArticleSummaryResponse>> findGroupedMapArticles(
            @RequestParam(value = "groupId") Long groupId,
            ArticleRangeRequest articleRangeRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        List<ArticleSummaryResponse> groupMapArticles = findArticleService.findGroupedMapArticles(
            groupId,
            articleRangeRequest.getLatitude(),
            articleRangeRequest.getLatitudeRange(),
            articleRangeRequest.getLongitude(),
            articleRangeRequest.getLongitudeRange(),
            userPrincipal.getUser()
        );
        return ResponseEntity.ok()
            .body(groupMapArticles);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ArticleResponse> findPublicArticle(@PathVariable Long id) {
        ArticleResponse articleResponse = findArticleService.findPublicArticle(id);

        return ResponseEntity.ok()
            .body(articleResponse);
    }

    @GetMapping("/private/{id}")
    public ResponseEntity<ArticleResponse> findPrivateArticle(@PathVariable Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        ArticleResponse articleResponse= findArticleService.findPrivateArticle(id, userPrincipal.getUser());

        return ResponseEntity.ok()
            .body(articleResponse);
    }

    @GetMapping("/grouped/{id}")
    public ResponseEntity<ArticleResponse> findGroupArticle(@PathVariable Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        ArticleResponse articleResponse= findArticleService.findGroupArticle(id, userPrincipal.getUser());

        return ResponseEntity.ok()
            .body(articleResponse);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<MyPageArticleResponse>> findMyArticles(@CurrentUser UserPrincipal userPrincipal){
        List<MyPageArticleResponse> myPageArticleResponse = findMyArticlesService.findMyArticles(userPrincipal.getId());

        return ResponseEntity.ok()
            .body(myPageArticleResponse);
    }
}
