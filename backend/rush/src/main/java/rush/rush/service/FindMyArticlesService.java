package rush.rush.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.dto.MyPageArticleResponse;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class FindMyArticlesService {

    final private ArticleRepository articleRepository;

    @Transactional
    public List<MyPageArticleResponse> findMyArticles(Long userId) {
        return articleRepository.findArticlesWithCommentsAndLikes(userId);
    }
}
