package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserBadgesDao;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import com.javamentor.qa.platform.service.abstracts.model.UserBadgesService;
import org.springframework.stereotype.Service;

@Service
public class UserBadgesServiceImpl extends ReadWriteServiceImpl<UserBadges, Long> implements UserBadgesService {
    public UserBadgesServiceImpl(UserBadgesDao userBadgesDao) {
        super(userBadgesDao);
    }
}
