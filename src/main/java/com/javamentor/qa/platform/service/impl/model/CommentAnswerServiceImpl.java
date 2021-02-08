package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    private final CommentAnswerDao commentAnswerDao;

    public CommentAnswerServiceImpl(ReadWriteDao<CommentAnswer, Long> readWriteDao, CommentAnswerDao commentAnswerDao) {
        super(readWriteDao);
        this.commentAnswerDao = commentAnswerDao;
    }

    @Transactional
    @Override
    public CommentAnswer addCommentToAnswer(String text, Answer answer, User user) {
        CommentAnswer commentAnswer = new CommentAnswer(text, user);
        commentAnswer.setAnswer(answer);

        commentAnswerDao.persist(commentAnswer);

        return commentAnswer;
    }
}
