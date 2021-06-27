package rush.rush.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String invitationCode;

    @CreationTimestamp
    private Timestamp createDate;

    public Group(String name, String invitationCode) {
        this(null, name, invitationCode, null);
    }

    public Group(Long id, String name, String invitationCode, Timestamp createDate) {
        validate(name, invitationCode);
        this.id = id;
        this.name = name;
        this.invitationCode = invitationCode;
        this.createDate = createDate;
    }

    private void validate(String name, String invitationCode) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("그룹이름이 null 일 수 없습니다.");
        }
        if (Objects.isNull(invitationCode)) {
            throw new IllegalArgumentException("초대코드가 null 일 수 없습니다.");
        }
    }
}
