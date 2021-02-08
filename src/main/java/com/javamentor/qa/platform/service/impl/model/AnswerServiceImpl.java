package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private SecurityHelper securityHelper;

    private AnswerService answerService;

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao) {
        super(readWriteDao);
    }

    @Autowired
    public void setSecurityHelper(SecurityHelper securityHelper) {
        this.securityHelper = securityHelper;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public boolean isQuestionBelongUser(Question question) {
        return question.getUser().getId().equals(securityHelper.getPrincipal().getId());
    }

    @Override
    public void markAnswerAsHelpful(Answer answer) {
        answer.setIsHelpful(true);
        answer.setDateAcceptTime(LocalDateTime.now());
        answerService.update(answer);
    }


}

