package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsOrderByNewTag")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsOrderByNewTagDaoImpl implements PaginationDao<TagListDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagListDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");
        LocalDateTime timeNow = LocalDateTime.now();

        return em.createQuery("select new " +
                "com.javamentor.qa.platform.models.dto.TagListDto(tag.id, tag.name, tag.description, count(q.id), " +
                "(select count(q.id) from tag.questions q where q.persistDateTime between :stDate1 and :endDate1), " +
                "(select count(q.id) from tag.questions q where q.persistDateTime between :stDate2 and :endDate2)) " +
                "from Tag tag left join tag.questions q group by tag.id order by tag.persistDateTime desc")
                .setParameter("stDate1", timeNow.minusDays(7))
                .setParameter("endDate1", timeNow)
                .setParameter("stDate2", timeNow.minusDays(1))
                .setParameter("endDate2", timeNow)
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int) (long) em.createQuery("select count(tag) from Tag tag").getSingleResult();
    }
}
