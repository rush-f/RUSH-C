package rush.rush.domain;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
@Builder
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

    public User(Long id, String nickName, String password, String email, String imageUrl,
        Timestamp joinDate, Timestamp visitDate, AuthProvider provider,
        String providerId) {
        validate(id, nickName, password, email, imageUrl, joinDate, visitDate,
            provider, providerId);
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.joinDate = joinDate;
        this.visitDate = visitDate;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void alterImageUrl(String newImageUrl) {
        imageUrl = newImageUrl;
    }

    public void alterNickName(String newNickName) {
        nickName = newNickName;
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
            throw new IllegalArgumentException("이메일 형식이 잘못되었습니다.");
        }
    }

    private boolean isEmailFormatRight(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        return matcher.matches();
    }

    private void validateNickName(String nickName) {
        if (Objects.isNull(nickName) || nickName.isEmpty()
            || nickName.length() > NICKNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임 형식이 잘못되었습니다.");
        }
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }
    }

    private void validateAuthProvider(AuthProvider provider) {
        if (Objects.isNull(provider)) {
            throw new IllegalArgumentException("provider 지정이 안되어있습니다.");
        }
    }
}
