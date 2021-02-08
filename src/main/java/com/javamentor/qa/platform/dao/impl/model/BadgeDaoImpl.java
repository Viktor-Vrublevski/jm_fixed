package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BadgeDao;
import com.javamentor.qa.platform.models.entity.Badge;
import org.springframework.stereotype.Repository;

@Repository
public class BadgeDaoImpl extends ReadWriteDaoImpl<Badge, Long> implements BadgeDao {
}
