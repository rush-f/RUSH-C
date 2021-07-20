package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.LikingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liking")
public class LikingController {

    private final LikingService likingService;

    @PostMapping("/article/Add")
    public ResponseEntity<Void> AddArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        likingService.AddArticleLiking(articleId, userPrincipal);

        return ResponseEntity
            .noContent()
            .build();
    }

    @DeleteMapping("article/delete")
    public ResponseEntity<Void> DeleteArticleLiking(@RequestParam(value = "article_id") Long articleId,
        @CurrentUser UserPrincipal userPrincipal){
        likingService.DeleteArticleLiking(articleId, userPrincipal.getId());

        return ResponseEntity
            .noContent()
            .build();
    }
}
