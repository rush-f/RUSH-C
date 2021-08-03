package rush.rush.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.dto.CommentResponse;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class FindCommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentResponse> findCommentsOfPublicArticle(Long articleId) {
        return commentRepository.findAllOfPublicArticle(articleId);
    }

    @Transactional
    public List<CommentResponse> findCommentsOfPrivateArticle(Long articleId, User user) {
        return commentRepository.findAllOfPrivateArticle(articleId, user.getId());
    }

    @Transactional
    public List<CommentResponse> findCommentsOfGroupedArticle(Long articleId, User user) {
        return commentRepository.findAllOfGroupedArticle(articleId, user.getId());
    }
}
