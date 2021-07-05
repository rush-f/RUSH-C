package rush.rush.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.dto.MyPageArticleResponse;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class FindMyArticlesService {

    final private ArticleRepository articleRepository;
    final private ArticleGroupRepository articleGroupRepository;

    public List<MyPageArticleResponse> findMyArticles(Long userId) {

        List<Article> articles = articleRepository.findAllByUserId(userId);

        return articles.stream()
            .map(article ->{
                List<GroupSummaryResponse> groups = findAllByArticle(article);
                return new MyPageArticleResponse(
                    article.getId(),
                    article.getTitle(),
                    article.isPublicMap(),
                    article.isPrivateMap(),
                    groups);
            })
            .collect(Collectors.toUnmodifiableList());
    }


    public List<GroupSummaryResponse> findAllByArticle(Article article) {

        List<ArticleGroup> articleGroups = articleGroupRepository.findAllByArticleId(article.getId());

        return articleGroups.stream()
            .map(articleGroup -> {
                Group group = articleGroup.getGroup();
                return new GroupSummaryResponse(group.getId(), group.getName());
            })
            .collect(Collectors.toUnmodifiableList());
    }
}
