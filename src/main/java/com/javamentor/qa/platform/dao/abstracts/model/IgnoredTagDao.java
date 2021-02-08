package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;

import java.util.*;

public interface IgnoredTagDao extends ReadWriteDao<IgnoredTag, Long>{
    List<IgnoredTag> getIgnoredTagsByUser(String name);

    void addIgnoredTag(IgnoredTag ignoredTag);

    Optional<IgnoredTag> getIgnoredTagDtoByName(Long id, String name);

    void deleteIgnoredTagByIdTagIdUser(Long id, Long tagId);
}
