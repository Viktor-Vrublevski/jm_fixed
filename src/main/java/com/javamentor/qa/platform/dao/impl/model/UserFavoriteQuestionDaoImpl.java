package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserFavoriteQuestionDao;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import org.springframework.stereotype.Repository;

@Repository
public class UserFavoriteQuestionDaoImpl extends ReadWriteDaoImpl<UserFavoriteQuestion, Long> implements UserFavoriteQuestionDao {
}
