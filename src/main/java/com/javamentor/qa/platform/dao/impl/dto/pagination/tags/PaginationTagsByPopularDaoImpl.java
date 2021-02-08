package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsByPopular")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsByPopularDaoImpl implements PaginationDao<TagDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        return em.createQuery(
                "select new com.javamentor.qa.platform.models.dto.TagDto(tag.id,tag.name)" +
                        " from Tag  tag order by tag.questions.size desc, tag.id ")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(tag) from Tag tag").getSingleResult();
    }
}
