package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface IgnoredTagService extends ReadWriteService<IgnoredTag, Long>{
    List<IgnoredTag> getIgnoredTagsByUser(User user);
    List<User> getUsersByIgnoredTag(Tag tag);
    void addIgnoredTagsToUser(List<Tag> tagList, User user);
    Optional<IgnoredTag> getIgnoredTagDtoByName(Long id, String name);
    @Transactional
    void deleteIgnoredTagByIdTagIdUser(Long id, Long tagId);
}
