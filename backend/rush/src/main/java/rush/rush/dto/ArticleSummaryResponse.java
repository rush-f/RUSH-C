package rush.rush.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArticleSummaryResponse {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String title;
}
