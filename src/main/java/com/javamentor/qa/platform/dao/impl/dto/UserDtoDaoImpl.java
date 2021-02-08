package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDto> getUserById(long id) {
        TypedQuery<UserDto> q = entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.UserDto(u.id, u.email, u.fullName," +
                        "u.imageLink, u.reputationCount)  " +
                        "from User u where u.id = :userId", UserDto.class)
                .setParameter("userId", id);
        return SingleResultUtil.getSingleResultOrNull(q);
    }

}
