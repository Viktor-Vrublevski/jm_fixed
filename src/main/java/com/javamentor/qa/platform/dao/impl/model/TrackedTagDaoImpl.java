package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TrackedTagDaoImpl extends ReadWriteDaoImpl<TrackedTag, Long>  implements TrackedTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrackedTag> getTrackedTagsByUser(String name) {
        return (List<TrackedTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT trackedTag FROM tag_tracked WHERE tag_tracked.user.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void addTrackedTag(TrackedTag trackedTag) {
        entityManager.persist(trackedTag);
    }

    @Override
    public Optional<TrackedTag> getTrackedTagDtoByName(Long id,String name) {
        return (Optional<TrackedTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT tr " +
                        "FROM TrackedTag tr " +
                        "INNER JOIN Tag tag on tag.name=tr.trackedTag.name " +
                        "INNER JOIN User u on u.id=tr.user.id " +
                        "WHERE u.id=:id and  tag.name=:name"
                )
                .setParameter("id", id)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    @Transactional
    public void deleteTrackedTagByIdTagIdUser(Long id, Long tagId) {
        entityManager.createQuery("DELETE FROM TrackedTag tr " +
                "WHERE tr.user.id=:id and tr.trackedTag.id=:tagId")
                .setParameter("id", id)
                .setParameter("tagId", tagId)
                .executeUpdate();
    }
}
