package rush.rush.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;

public class SetUpMethods {

    public static User persistUser(TestEntityManager testEntityManager, String email) {
        User user = User.builder()
            .email(email)
            .password("test password")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();
        user = testEntityManager.persist(user);
        testEntityManager.flush();
        return user;
    }

    public static Group persistGroup(TestEntityManager testEntityManager) {
        Group group = Group.builder()
            .name(Constants.TEST_GROUP_NAME)
            .build();
        group = testEntityManager.persist(group);
        group.setInvitationCode("ABCDE" + group.getId());
        testEntityManager.flush();
        return group;
    }

    public static void persistUserGroup(TestEntityManager testEntityManager, User user, Group group) {
        UserGroup userGroup = UserGroup.builder()
            .group(group)
            .user(user)
            .build();
        testEntityManager.persist(userGroup);
        testEntityManager.flush();
    }

    public static Article persistArticle(TestEntityManager testEntityManager, User user,
            boolean isPublicMap, boolean isPrivateMap,
            Double latitude, Double longitude) {
        Article article = Article.builder()
            .user(user)
            .title("글제목")
            .content("내용내용")
            .latitude(latitude)
            .longitude(longitude)
            .publicMap(isPublicMap)
            .privateMap(isPrivateMap)
            .build();
        article = testEntityManager.persist(article);
        testEntityManager.flush();
        return article;
    }
}
