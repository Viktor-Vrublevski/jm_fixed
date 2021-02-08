package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;

import java.util.List;
import java.util.Optional;


public interface TrackedTagDao extends ReadWriteDao<TrackedTag, Long>{
    List<TrackedTag> getTrackedTagsByUser(String name);
    void addTrackedTag(TrackedTag trackedTag);
    Optional<TrackedTag> getTrackedTagDtoByName(Long id, String name);
    void deleteTrackedTagByIdTagIdUser(Long id, Long tagId);
}
