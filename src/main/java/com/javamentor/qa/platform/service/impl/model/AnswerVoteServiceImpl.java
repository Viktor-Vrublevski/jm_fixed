package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerVoteDao;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.service.abstracts.model.AnswerVoteService;
import org.springframework.stereotype.Service;

@Service
public class AnswerVoteServiceImpl extends ReadWriteServiceImpl<AnswerVote, Long> implements AnswerVoteService {


    public AnswerVoteServiceImpl(AnswerVoteDao answerVoteDao) {
        super(answerVoteDao);
    }
}
