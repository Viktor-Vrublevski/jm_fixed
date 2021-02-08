package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserFavoriteQuestionDao;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import com.javamentor.qa.platform.service.abstracts.model.UserFavoriteQuestionService;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteQuestionServiceImpl extends ReadWriteServiceImpl<UserFavoriteQuestion, Long> implements UserFavoriteQuestionService {
    public UserFavoriteQuestionServiceImpl(UserFavoriteQuestionDao userFavoriteQuestionDao) {
        super(userFavoriteQuestionDao);
    }
}
