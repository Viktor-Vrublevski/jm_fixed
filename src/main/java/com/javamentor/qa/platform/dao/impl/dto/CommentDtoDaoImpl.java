package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.dto.CommentQuestionDto;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CommentQuestionDto> getAllCommentsByQuestionId(Long questionId) {

        return (List<CommentQuestionDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.CommentQuestionDto(" +
                        "cq.id, " +
                        "cq.question.id, " +
                        "cq.comment.lastUpdateDateTime, " +
                        "cq.comment.persistDateTime, " +
                        "cq.comment.text, " +
                        "cq.comment.user.id) FROM CommentQuestion as cq WHERE cq.question.id = :questionId")
                .setParameter("questionId", questionId)
                .unwrap(org.hibernate.query.Query.class)
                .getResultList();
    }

    @Override
    public List<CommentAnswerDto> getAllCommentsByAnswerId(Long answerId) {

        return (List<CommentAnswerDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.CommentAnswerDto(" +
                        "ca.id, " +
                        "ca.answer.id, " +
                        "ca.comment.lastUpdateDateTime, " +
                        "ca.comment.persistDateTime, " +
                        "ca.comment.text, " +
                        "ca.comment.user.id) FROM CommentAnswer as ca WHERE ca.answer.id = :answerId")
                .setParameter("answerId", answerId)
                .unwrap(org.hibernate.query.Query.class)
                .getResultList();
    }
}
