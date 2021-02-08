package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;


public interface CommentQuestionService extends ReadWriteService<CommentQuestion, Long> {

     CommentQuestion addCommentToQuestion(String commentText, Question question, User user);
}
