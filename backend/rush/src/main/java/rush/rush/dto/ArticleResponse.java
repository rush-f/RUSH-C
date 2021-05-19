package rush.rush.dto;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private ArticleAuthorResponse author;
    private Timestamp createDate;
}
