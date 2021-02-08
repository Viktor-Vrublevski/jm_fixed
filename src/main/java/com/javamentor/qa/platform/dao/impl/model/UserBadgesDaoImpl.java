package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserBadgesDao;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import org.springframework.stereotype.Repository;

@Repository
public class UserBadgesDaoImpl extends ReadWriteDaoImpl <UserBadges, Long> implements UserBadgesDao {
}
