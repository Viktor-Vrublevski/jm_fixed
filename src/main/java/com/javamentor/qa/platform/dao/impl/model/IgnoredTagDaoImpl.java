package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class IgnoredTagDaoImpl  extends ReadWriteDaoImpl<IgnoredTag, Long>  implements IgnoredTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IgnoredTag> getIgnoredTagsByUser(String name) {
        return (List<IgnoredTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT ignoredTag FROM tag_ignore WHERE tag_ignore.user.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void addIgnoredTag(IgnoredTag ignoredTag) {
        entityManager.persist(ignoredTag);
    }

    @Override
    public Optional<IgnoredTag> getIgnoredTagDtoByName(Long id, String name) {
        return (Optional<IgnoredTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT tr " +
                        "FROM IgnoredTag tr " +
                        "INNER JOIN Tag tag on tag.name=tr.ignoredTag.name " +
                        "INNER JOIN User u on u.id=tr.user.id " +
                        "WHERE u.id=:id and  tag.name=:name"
                )
                .setParameter("id", id)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    @Transactional
    public void deleteIgnoredTagByIdTagIdUser(Long id, Long tagId) {
         entityManager.createQuery("DELETE FROM IgnoredTag ig " +
                                "WHERE ig.user.id=:id and ig.ignoredTag.id=:tagId"
                )
                .setParameter("id", id)
                .setParameter("tagId", tagId)
                 .executeUpdate();
    }
}
