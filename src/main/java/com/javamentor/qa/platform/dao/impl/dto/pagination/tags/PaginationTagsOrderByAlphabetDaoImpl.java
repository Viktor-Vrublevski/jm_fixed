package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsOrderByAlphabet")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsOrderByAlphabetDaoImpl implements PaginationDao<TagListDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagListDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        String query = "Select new com.javamentor.qa.platform.models.dto.TagListDto(t.id, t.name, t.description, " +
                " count(q.id) ," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate1 AND :edDate1 or t.questions.size = 0) ," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate2 AND :edDate2 or t.questions.size = 0))" +
                " from Tag t left join t.questions  q" +
                " group by t.id" +
                " order by t.name";

        LocalDateTime timeNow = LocalDateTime.now();

        return em.createQuery(query)
                .setParameter("stDate1", timeNow.minusDays(7))
                .setParameter("edDate1", timeNow)
                .setParameter("stDate2", timeNow.minusDays(1))
                .setParameter("edDate2", timeNow)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(tag) from Tag tag").getSingleResult();
    }
}
