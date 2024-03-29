package rush.rush.domain;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import rush.rush.exception.WrongInputException;

@Entity
@Getter
@NoArgsConstructor
public class User {

    private static final int NICKNAME_MAX_LENGTH = 100;
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final String EMAIL_FORMAT = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_FORMAT);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NICKNAME_MAX_LENGTH)
    private String nickName;

    @NotNull
    private String password;

    @Column(nullable = false, length = EMAIL_MAX_LENGTH, unique = true)
    private String email;

    private String imageUrl;

    @CreationTimestamp
    private Timestamp joinDate;

    @CreationTimestamp
    private Timestamp visitDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ArticleLike> articleLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<UserGroup> userGroups = new ArrayList<>();

    @Builder
    public User(Long id, String nickName, String password, String email, String imageUrl,
        Timestamp joinDate, Timestamp visitDate, AuthProvider provider, String providerId,
        List<Article> articles, List<Comment> comments, List<ArticleLike> articleLikes,
        List<CommentLike> commentLikes, List<UserGroup> userGroups) {
        validate(id, nickName, password, email, imageUrl, joinDate, visitDate, provider,
            providerId);
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.joinDate = joinDate;
        this.visitDate = visitDate;
        this.provider = provider;
        this.providerId = providerId;
        if (Objects.nonNull(articles)) {
            this.articles = articles;
        }
        if (Objects.nonNull(comments)) {
            this.comments = comments;
        }
        if (Objects.nonNull(articleLikes)) {
            this.articleLikes = articleLikes;
        }
        if (Objects.nonNull(commentLikes)) {
            this.commentLikes = commentLikes;
        }
        if (Objects.nonNull(userGroups)) {
            this.userGroups = userGroups;
        }
    }

    private void validate(Long id, String nickName, String password, String email, String imageUrl,
        Timestamp joinDate, Timestamp visitDate, AuthProvider provider,
        String providerId) {
        validateNickName(nickName);
        validatePassword(password);
        validateAuthProvider(provider);
        validateEmail(email);
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isEmpty()
            || email.length() > EMAIL_MAX_LENGTH || !isEmailFormatRight(email)) {
            throw new WrongInputException("이메일 형식이 잘못되었습니다.");
        }
    }

    private boolean isEmailFormatRight(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        return matcher.matches();
    }

    private void validateNickName(String nickName) {
        if (Objects.isNull(nickName) || nickName.isEmpty()
            || nickName.length() > NICKNAME_MAX_LENGTH) {
            throw new WrongInputException("닉네임 형식이 잘못되었습니다.");
        }
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new WrongInputException("비밀번호가 잘못되었습니다.");
        }
    }

    private void validateAuthProvider(AuthProvider provider) {
        if (Objects.isNull(provider)) {
            throw new WrongInputException("provider 지정이 안되어있습니다.");
        }
    }

    public void addArticle(Article newArticle) {
        if (isAlreadyExist(newArticle)) {
            return;
        }
        articles.add(newArticle);
    }

    private boolean isAlreadyExist(Article newArticle) {
        return articles.stream()
            .anyMatch(article -> article.getId()
                .equals(newArticle.getId()));
    }

    public void addComment(Comment newComment) {
        if (isAlreadyExist(newComment)) {
            return;
        }
        comments.add(newComment);
    }

    private boolean isAlreadyExist(Comment newComment) {
        return comments.stream()
            .anyMatch(comment -> comment.getId()
                .equals(newComment.getId()));
    }

    public void addArticleLike(ArticleLike newArticleLike) {
        if (isAlreadyExist(newArticleLike)) {
            return;
        }
        articleLikes.add(newArticleLike);
    }

    private boolean isAlreadyExist(ArticleLike newArticleLike) {
        return articleLikes.stream()
            .anyMatch(articleLike -> articleLike.getId()
                .equals(newArticleLike.getId()));
    }

    public void addCommentLike(CommentLike newCommentLike) {
        if (isAlreadyExist(newCommentLike)) {
            return;
        }
        commentLikes.add(newCommentLike);
    }

    private boolean isAlreadyExist(CommentLike newCommentLike) {
        return commentLikes.stream()
            .anyMatch(commentLike -> commentLike.getId()
                .equals(newCommentLike.getId()));
    }

    public void addUserGroup(UserGroup newUserGroup) {
        if (isAlreadyExist(newUserGroup)) {
            return;
        }
        userGroups.add(newUserGroup);
    }

    private boolean isAlreadyExist(UserGroup newUserGroup) {
        return userGroups.stream()
            .anyMatch(userGroup -> userGroup.getId()
                .equals(newUserGroup.getId()));
    }
}
