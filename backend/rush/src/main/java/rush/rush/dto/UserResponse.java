package rush.rush.dto;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class UserResponse {

    private Long userId;
    private String imageUrl;
    private String nickName;
    private String email;
    private Timestamp visitDate;
}
