package rush.rush.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.dto.MyPageArticleResponse;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class FindMyArticlesService {

    final private ArticleRepository articleRepository;

    @Transactional
    public List<MyPageArticleResponse> findMyArticles(Long userId) {
        List<Article> articles = articleRepository.findArticlesWithComments(userId);

        return articles.stream()
            .map(article -> new MyPageArticleResponse(
                article.getId(),
                article.getTitle(),
                article.isPublicMap(),
                article.isPrivateMap(),
                article.getCreateDate(),
                article.getArticleLikes().stream().count(),
                article.getComments().stream().count()
            ))
            .collect(Collectors.toUnmodifiableList());
    }
}
