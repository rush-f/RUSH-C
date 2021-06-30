package rush.rush.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.domain.Article;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.dto.FindMapArticlesRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CreateArticleService;
import rush.rush.service.FindArticleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final CreateArticleService createArticleService;
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

    @GetMapping("/public/{id}")
    public ResponseEntity<ArticleResponse> findPublicArticle(@PathVariable Long id) {
        ArticleResponse articleResponse = findArticleService.findPublicArticle(id);

        return ResponseEntity.ok()
            .body(articleResponse);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateArticleRequest createArticleRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        Article article = createArticleService.create(createArticleRequest, userPrincipal.getUser());

        if (article.isPublicMap()) {
            return buildArticleCreateResponse("/articles/public/" + article.getId());
        }
        if (article.isPrivateMap()) {
            return buildArticleCreateResponse("/articles/private/" + article.getId());
        }
        return buildArticleCreateResponse("/articles/grouped/" + article.getId());
    }

    private ResponseEntity<Void> buildArticleCreateResponse(String uri) {
        return ResponseEntity.created(URI.create(uri))
            .build();
    }
}
