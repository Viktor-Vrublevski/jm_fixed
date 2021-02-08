package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationQuestionWithoutAnswers")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionWithoutAnswersDaoImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {

        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");

        List<Long> questionIds = (List<Long>) em
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) where a.question.id is null")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

        return (List<QuestionDto>) em.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        " question.title as question_title," +
                        "u.fullName as question_authorName," +
                        " u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        " question.viewCount as question_viewCount," +
                        "(select count(a.question.id) from Answer a where a.question.id=question_id) as question_countAnswer," +
                        "(select count(v.question.id) from VoteQuestion v where v.question.id=question_id) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name " +
                        "from Question question  " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag" +
                        " where question_id IN :ids")
                .setParameter("ids", questionIds)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count (q.id)" +
                        " from Question q left outer join Answer a on (q.id = a.question.id)" +
                        " where a.question.id is null").getSingleResult();
    }
}
