package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsRecentById")
@SuppressWarnings(value = "unchecked")
public class PaginationTagsRecentByIdDaoImpl implements PaginationDao<TagRecentDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagRecentDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");
        long id = (long)parameters.get("tagId");

        return em
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.TagRecentDto(t.childTag.id, t.childTag.name, COUNT(t.childTag)) " +
                        "FROM RelatedTag t " +
                        "GROUP BY t.mainTag, t.childTag, t.childTag.name " +
                        "HAVING t.mainTag.id =:id " +
                        "ORDER BY COUNT(t.childTag) DESC ")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        long id = (long)parameters.get("tagId");
        return (int)(long) em.createQuery("select count(tag.childTag) from RelatedTag tag where tag.mainTag.id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
