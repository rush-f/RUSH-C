package rush.rush.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.domain.Article;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.CreateArticleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class CreateArticleController {

    private final CreateArticleService createArticleService;

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
