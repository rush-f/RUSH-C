package rush.rush.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.User;
import rush.rush.dto.AuthorResponse;
import rush.rush.dto.CommentResponse;
import rush.rush.dto.CreateCommentRequest;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository; // Todo : 없애기

    @Transactional
    public CommentResponse create(Long articleId, CreateCommentRequest createCommentRequest, User user) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 article ID 입니다."));

        Comment savedComment = commentRepository.save(
            new Comment(createCommentRequest.getContent(), user, article));

        return toCommentResponse(savedComment);
    }

    @Transactional
    public List<CommentResponse> findCommentsByArticleId(Long articleId) {

        return commentRepository.findAllByArticleId(articleId).stream()
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
