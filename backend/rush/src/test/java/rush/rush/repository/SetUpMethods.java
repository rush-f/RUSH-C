package rush.rush.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Comment;
import rush.rush.domain.CommentLike;
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
        return testEntityManager.persist(user);
    }

    public static Group persistGroup(TestEntityManager testEntityManager) {
        Group group = Group.builder()
            .name(Constants.TEST_GROUP_NAME)
            .build();
        group = testEntityManager.persist(group);
        group.setInvitationCode("ABCDE" + group.getId());
        return group;
    }

    public static void persistUserGroup(TestEntityManager testEntityManager, User user, Group group) {
        UserGroup userGroup = UserGroup.builder()
            .group(group)
            .user(user)
            .build();
        testEntityManager.persist(userGroup);
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
        return testEntityManager.persist(article);
    }

    public static void persistArticleGroup(TestEntityManager testEntityManager, Article article, Group group) {
        ArticleGroup articleGroup = ArticleGroup.builder()
            .article(article)
            .group(group)
            .build();
        testEntityManager.persist(articleGroup);
    }

    public static CommentLike persistCommentLike(TestEntityManager testEntityManager, User user, Comment comment) {
        CommentLike commentLike =CommentLike.builder()
            .user(user)
            .comment(comment)
            .build();
        return testEntityManager.persist(commentLike);
    }
}
