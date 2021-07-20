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

@Service
@RequiredArgsConstructor
public class ArticleLikingService {

    private final ArticleLikingRepository articleLikingRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addArticleLiking(Long articleId, Long userId) {
        User user = findUser(userId);
        Article article = findArticle(articleId);

        articleLikingRepository.save(new ArticleLiking(user, article));
    }

    @Transactional
    public void deleteArticleLiking(Long articleId, Long userId) {

        articleLikingRepository.delete(findArticleLiking(articleId,userId));
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 유저가 없습니다!"));
    }

    private Article findArticle(Long articleId){
        return articleRepository.findById(articleId)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 게시글이 없습니다"));
    }

    private ArticleLiking findArticleLiking(Long articleId, Long userId){
        return  articleLikingRepository.findByUserIdAndArticleId(userId, articleId)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 좋아요는 없습니다"));
    }
}
