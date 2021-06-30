package rush.rush.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class CreateArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleGroupRepository articleGroupRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public Long create(CreateArticleRequest createArticleRequest, User user) {
        Article article = articleRepository.save(buildArticle(createArticleRequest, user));

        List<Long> groupIds = createArticleRequest.getGroupIdsToBeIncluded();
        List<Group> groups = findMyGroups(groupIds, user);

        for (Group group : groups) {
            articleGroupRepository.save(buildArticleGroup(article, group));
        }
        return article.getId();
    }

    private Article buildArticle(CreateArticleRequest createArticleRequest, User user) {
        return Article.builder()
            .title(createArticleRequest.getTitle())
            .content(createArticleRequest.getContent())
            .user(user)
            .latitude(createArticleRequest.getLatitude())
            .longitude(createArticleRequest.getLongitude())
            .doesBelongToPublic(createArticleRequest.isDoesBelongToPublic())
            .doesBelongToPrivate(createArticleRequest.isDoesBelongToPrivate())
            .build();
    }

    private List<Group> findMyGroups(List<Long> groupIds, User user) {
        if (Objects.isNull(groupIds) || groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> myGroupIds = userGroupRepository.findAllByUserId(user.getId()).stream()
            .map(userGroup -> userGroup.getGroup().getId())
            .collect(Collectors.toUnmodifiableList());

        List<Group> groups = groupRepository.findAllById(groupIds).stream()
            .filter(group -> myGroupIds.contains(group.getId()))
            .collect(Collectors.toUnmodifiableList());

        if (groups.size() != groupIds.size()) {
            throw new IllegalArgumentException("그룹 id 목록이 잘못됐습니다.");
        }
        return groups;
    }

    private ArticleGroup buildArticleGroup(Article article, Group group) {
        return ArticleGroup.builder()
            .group(group)
            .article(article)
            .build();
    }
}
