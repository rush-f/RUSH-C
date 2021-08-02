package rush.rush.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rush.rush.domain.Article;
import rush.rush.domain.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArticleDetails {

    private Article article;
    private User user;
    private Long totalLikes;
}
