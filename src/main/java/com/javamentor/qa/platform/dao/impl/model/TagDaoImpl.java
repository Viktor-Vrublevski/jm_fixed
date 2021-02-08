package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends ReadWriteDaoImpl<Tag, Long> implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> getTagByName(String name) {
        return (Optional<Tag>) entityManager.unwrap(Session.class).createQuery("SELECT tag FROM Tag tag WHERE tag.name = :name")
                .setParameter("name", name).uniqueResultOptional();
    }

    @Override
    public void addTagToQuestion(Tag tag) {
        entityManager.persist(tag);
    }
}
