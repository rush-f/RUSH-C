package rush.rush.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import rush.rush.exception.WrongInputException;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "GROUP_TABLE")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String invitationCode;

    @CreationTimestamp
    private Timestamp createDate;


    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    private List<ArticleGroup> articleGroups = new ArrayList<>();

    @Builder
    public Group(Long id, String name, String invitationCode, Timestamp createDate,
        List<UserGroup> userGroups, List<ArticleGroup> articleGroups) {
        validate(name);
        this.id = id;
        this.name = name;
        this.invitationCode = invitationCode;
        this.createDate = createDate;
        if (Objects.nonNull(userGroups)) {
            this.userGroups = userGroups;
        }
        if (Objects.nonNull(articleGroups)) {
            this.articleGroups = articleGroups;
        }
    }

    private void validate(String name) {
        if (Objects.isNull(name)) {
            throw new WrongInputException("그룹이름이 null 일 수 없습니다.");
        }
    }

    public void setInvitationCode(String invitationCode) {
        if (Objects.nonNull(this.invitationCode) && !this.invitationCode.isEmpty()) {
            throw new WrongInputException("초대코드는 처음 한번만 정할 수 있습니다. 이미 부여된 초대코드가 존재합니다.");
        }
        this.invitationCode = invitationCode;
    }

    public void addArticleGroup(ArticleGroup newArticleGroup) {
        if (isAlreadyExist(newArticleGroup)) {
            return;
        }
        articleGroups.add(newArticleGroup);
    }

    private boolean isAlreadyExist(ArticleGroup newArticleGroup) {
        return articleGroups.stream()
            .anyMatch(articleGroup -> articleGroup.getId().equals(newArticleGroup.getId()));
    }
}
