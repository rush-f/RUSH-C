package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.User;
import rush.rush.repository.ArticleLikeRepository;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void changeMyLike(Long articleId, Boolean hasILiked, User user) {
        if(hasILiked){
            articleLikeRepository.delete(findLike(articleId,user.getId()));
        }
        else{
            Article article = findArticle(articleId);
            articleLikeRepository.save(ArticleLike.builder()
                .user(user)
                .article(article)
                .build());
        }
    }

    @Transactional
    public boolean hasILiked(Long articleId, Long userId) {
        int count = articleLikeRepository.countByUserIdAndArticleId(userId,articleId);

        return count >= 1;
    }

    private Article findArticle(Long articleId){
        return articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 게시글이 없습니다"));
    }

    private ArticleLike findLike(Long articleId, Long userId){
        return  articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 좋아요는 없습니다"));
    }

}
