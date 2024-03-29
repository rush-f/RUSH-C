package rush.rush.domain;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import rush.rush.exception.WrongInputException;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "GROUP_ID"})})
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;

    private Boolean important;

    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public UserGroup(Long id, User user, Group group, Timestamp createDate, Boolean important) {
        validate(user);
        validate(group);
        this.id = id;
        this.user = user;
        this.group = group;
        this.createDate = createDate;
        this.important = important;
    }

    private void validate(User user) {
        if (Objects.isNull(user)) {
            throw new WrongInputException("user 가 null 입니다.");
        }
        if (Objects.isNull(user.getId())) {
            throw new WrongInputException("user 객체에 userId가 없습니다.");
        }
    }

    private void validate(Group group) {
        if (Objects.isNull(group)) {
            throw new WrongInputException("group 이 null 입니다.");
        }
        if (Objects.isNull(group.getId())) {
            throw new WrongInputException("group 객체에 groupId가 없습니다.");
        }
    }

    public void changeImportant() {
        if (Objects.isNull(important)) {
            important = true;
        } else {
            important = !important;
        }
    }
}
