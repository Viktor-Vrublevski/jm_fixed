package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface TrackedTagService extends ReadWriteService<TrackedTag, Long>{
    List<TrackedTag> getTrackedTagsByUser(User user);
    List<User> getUsersByTrackedTag(Tag tag);
    void addTrackedTagsToUser(List<Tag> tagList, User user);
    Optional<TrackedTag> getTrackedTagDtoByName(Long id, String name);
    void deleteTrackedTagByIdTagIdUser(Long id, Long tagId);
}
