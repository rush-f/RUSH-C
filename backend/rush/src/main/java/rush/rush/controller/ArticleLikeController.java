package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.ArticleLikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{id}/like")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @PostMapping
    public ResponseEntity<Void> changeMyLike(@PathVariable Long id,
        @RequestParam(value = "hasiliked") Boolean hasILiked,
        @CurrentUser UserPrincipal userPrincipal){
        articleLikeService.changeMyLike(id,hasILiked, userPrincipal.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @GetMapping
    public ResponseEntity<Boolean> checkMyLike(@PathVariable Long id,
        @CurrentUser UserPrincipal userPrincipal){
        boolean result= articleLikeService.hasILiked(id, userPrincipal.getId());

        return ResponseEntity.ok()
            .body(result);
    }
}
