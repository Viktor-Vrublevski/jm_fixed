package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<IgnoredTagDto> getIgnoredTagsByPrincipal(Long id) {
        return (List<IgnoredTagDto>) entityManager.unwrap(Session.class)
                .createQuery("select new com.javamentor.qa.platform.models.dto.IgnoredTagDto(i.ignoredTag.id, i.ignoredTag.name) " +
                        "from IgnoredTag as i " +
                        "inner join Tag t on t.name=i.ignoredTag.name " +
                        "and t.id=i.ignoredTag.id " +
                        "inner join User u on u.id=i.user.id " +
                        "where u.id=:id")
                .setParameter("id", id)
                .getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<TrackedTagDto> getTrackedTagsByPrincipal(Long id) {
        return (List<TrackedTagDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.TrackedTagDto(i.trackedTag.id, i.trackedTag.name) " +
                        "FROM TrackedTag as i " +
                        "INNER JOIN Tag t on t.name=i.trackedTag.name " +
                        "AND t.id=i.trackedTag.id " +
                        "INNER JOIN User u on u.id=i.user.id " +
                        "WHERE u.id=:id")
                .setParameter("id", id)
                .getResultList();
    }

}
