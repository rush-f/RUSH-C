package rush.rush.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArticleAuthorResponse {

    private Long id;
    private String nickName;
    private String imageUrl;
}
