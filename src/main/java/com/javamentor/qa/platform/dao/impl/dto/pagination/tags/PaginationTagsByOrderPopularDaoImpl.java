package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsByOrderPopular")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsByOrderPopularDaoImpl implements PaginationDao<TagListDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagListDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        return em.createQuery("" +
                "SELECT new com.javamentor.qa.platform.models.dto.TagListDto(t.id, t.name, t.description, COUNT(q.id), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate1 AND :endDate1), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate2 AND :endDate2)) " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "GROUP BY t.id " +
                "ORDER BY COUNT(q.id) DESC")
                .setParameter("startDate1", LocalDateTime.now().minusDays(7))
                .setParameter("endDate1", LocalDateTime.now())
                .setParameter("startDate2", LocalDateTime.now().minusDays(1))
                .setParameter("endDate2", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(tag) from Tag tag").getSingleResult();
    }
}
