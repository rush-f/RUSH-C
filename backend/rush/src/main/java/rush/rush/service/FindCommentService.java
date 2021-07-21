package rush.rush.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Comment;
import rush.rush.domain.User;
import rush.rush.dto.AuthorResponse;
import rush.rush.dto.CommentResponse;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class FindCommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public List<CommentResponse> findCommentsOfPublicArticle(Long articleId) {
        return commentRepository.findAllOfPublicArticle(articleId)
            .stream()
            .map(this::toCommentResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentResponse> findCommentsOfPrivateArticle(Long articleId, User user) {
        return commentRepository.findAllOfPrivateArticle(articleId, user.getId())
            .stream()
            .map(this::toCommentResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentResponse> findCommentsOfGroupedArticle(Long articleId, User user) {
        return commentRepository.findAllOfGroupedArticle(articleId, user.getId())
            .stream()
            .map(this::toCommentResponse)
            .collect(Collectors.toList());
    }

    private CommentResponse toCommentResponse(Comment comment){
        AuthorResponse authorResponse = toAuthResponse(comment.getUser());

        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            authorResponse,
            comment.getCreateDate()
        );
    }

    private AuthorResponse toAuthResponse(User author) {
        return new AuthorResponse(
            author.getId(),
            author.getNickName(),
            author.getImageUrl()
        );
    }
}
