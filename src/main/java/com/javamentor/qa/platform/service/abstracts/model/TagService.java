package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagService extends ReadWriteService<Tag, Long> {

    void addTagToQuestion(List<Long> tagId, Question question);
    Optional<Tag> getTagByName(String name);
}
