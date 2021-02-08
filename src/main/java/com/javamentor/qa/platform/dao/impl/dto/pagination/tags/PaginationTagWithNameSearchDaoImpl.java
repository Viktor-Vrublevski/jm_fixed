package com.javamentor.qa.platform.dao.impl.dto.pagination.tags;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationTagsWithNameSearch")
@SuppressWarnings(value = "unchecked")
public class PaginationTagWithNameSearchDaoImpl implements PaginationDao<TagListDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagListDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");
        String tagName = (String) parameters.get("tagName");

        List<TagListDto> tagListDtos = em.createQuery("" +
                "SELECT new com.javamentor.qa.platform.models.dto.TagListDto(t.id, t.name, t.description, COUNT(q.id), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate1 AND :endDate1), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate2 AND :endDate2)) " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "where UPPER(t.name) like concat('%',UPPER(:tagName),'%' ) " +
                "GROUP BY t.id")
                .setParameter("startDate1", LocalDateTime.now().minusDays(7))
                .setParameter("endDate1", LocalDateTime.now())
                .setParameter("startDate2", LocalDateTime.now().minusDays(1))
                .setParameter("endDate2", LocalDateTime.now())
                .setParameter("tagName", tagName)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
        System.out.println(parameters.get("tagName"));
        System.out.println("OLOLOLO1---"+tagListDtos.size());
        tagListDtos.forEach(elem -> System.out.println("UUU  "+elem.getName()));
        System.out.println("OLOLOLO2");

        return tagListDtos;
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        String tagName = (String)parameters.get("tagName");
        return (int) (long) em.createQuery("select count(e) from Tag e where UPPER(e.name) LIKE CONCAT('%',UPPER(:tagName),'%')")
                .setParameter("tagName", tagName)
                .getSingleResult();
    }
}
