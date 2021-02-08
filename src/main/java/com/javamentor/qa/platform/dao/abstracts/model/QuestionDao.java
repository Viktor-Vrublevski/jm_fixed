package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import java.util.List;
import java.util.Map;

public interface QuestionDao extends ReadWriteDao<Question, Long> {

    List<Tag> getAllTagOfQuestion(Question question);

    List<Long> getPaginationQuestionIds(int page, int size);

}
