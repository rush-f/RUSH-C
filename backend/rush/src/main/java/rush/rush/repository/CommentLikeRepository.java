package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

}
