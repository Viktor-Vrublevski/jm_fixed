package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerVoteDao;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerVoteDaoImpl extends ReadWriteDaoImpl<AnswerVote, Long> implements AnswerVoteDao {
}
