package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsRecent")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsRecentDaoImpl implements PaginationDao<TagRecentDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagRecentDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        return em.createQuery("" +
                "SELECT new com.javamentor.qa.platform.models.dto.TagRecentDto(t.id, t.name, " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :start AND :end)) " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "GROUP BY t.id " +
                "ORDER BY COUNT(q.id) DESC")
                .setParameter("start", LocalDateTime.now().minusMonths(1))
                .setParameter("end", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(tag) from Tag tag").getSingleResult();
    }
}
