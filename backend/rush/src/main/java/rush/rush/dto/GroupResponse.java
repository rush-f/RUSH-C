package rush.rush.dto;

import java.util.Date;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GroupResponse {

    private Long id;
    private String name;
    private String invitationCode;
    private Boolean important;
    private Date createDate;

    public GroupResponse(Long id, String name, String invitationCode, Boolean important, Date createDate) {
        if (Objects.isNull(important)) {
            this.important = false;
        } else {
            this.important = important;
        }
        this.id = id;
        this.name = name;
        this.invitationCode = invitationCode;
        this.createDate = createDate;
    }
}
