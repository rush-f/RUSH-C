package rush.rush.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "GROUP_ID"})})
@Builder
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;

    @CreationTimestamp
    private Timestamp createDate;

    public UserGroup(User user, Group group) {
        this(null, user, group, null);
    }

    public UserGroup(Long id, User user, Group group, Timestamp createDate) {
        validate(user);
        validate(group);

        this.id = id;
        this.user = user;
        this.group = group;
        this.createDate = createDate;
    }

    private void validate(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("user 가 null 입니다.");
        }
        if (Objects.isNull(user.getId())) {
            throw new IllegalArgumentException("user 객체에 userId가 없습니다.");
        }
    }

    private void validate(Group group) {
        if (Objects.isNull(group)) {
            throw new IllegalArgumentException("group 이 null 입니다.");
        }
        if (Objects.isNull(group.getId())) {
            throw new IllegalArgumentException("group 객체에 groupId가 없습니다.");
        }
    }
}
