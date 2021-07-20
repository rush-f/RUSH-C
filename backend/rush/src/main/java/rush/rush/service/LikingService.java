package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLiking;
import rush.rush.domain.User;
import rush.rush.repository.ArticleLikingRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserRepository;
import rush.rush.security.user.UserPrincipal;

@Service
@RequiredArgsConstructor
public class LikingService {

    private final ArticleLikingRepository articleLikingRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public void AddArticleLiking(Long articleId, UserPrincipal userPrincipal) {
        User user = findUser(userPrincipal.getId());
        Article article = findArticle(articleId);

        articleLikingRepository.save(new ArticleLiking(user, article));
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 유저가 없습니다!"));
    }
    private Article findArticle(Long articleId){
        return articleRepository.findById(articleId)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 게시글이 없습니다"));
    }
}
