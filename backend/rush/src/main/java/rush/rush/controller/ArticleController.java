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
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.ArticleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/public")
    public ResponseEntity<List<ArticleSummaryResponse>> findPublicMapArticles(
        @RequestParam(value = "latitude", defaultValue = "37.631686026257206") Double latitude,
        @RequestParam(value = "longitude", defaultValue = "127.07747664827662") Double longitude
        ) {
        List<ArticleSummaryResponse> publicMapArticles =
            articleService.findPublicMapArticles(latitude, longitude);

        // Todo:지울것
        System.out.println("위도 : " + latitude + ", 경도 : " + longitude);

        return ResponseEntity.ok()
            .body(publicMapArticles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findOne(@PathVariable Long id) {
        ArticleResponse articleResponse = articleService.findOne(id);

        return ResponseEntity.ok()
            .body(articleResponse);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateArticleRequest createArticleRequest,
        @CurrentUser UserPrincipal userPrincipal) {
        Long articleId = articleService.create(createArticleRequest, userPrincipal.getUser());

        return ResponseEntity.created(URI.create("/articles/" + articleId))
            .build();
    }
}
