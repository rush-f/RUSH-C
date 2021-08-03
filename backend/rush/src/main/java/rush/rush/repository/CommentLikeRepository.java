package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);
}
