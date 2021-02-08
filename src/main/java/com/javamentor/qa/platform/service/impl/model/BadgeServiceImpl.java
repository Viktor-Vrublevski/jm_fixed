package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BadgeDao;
import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.service.abstracts.model.BadgeService;
import org.springframework.stereotype.Service;

@Service
public class BadgeServiceImpl extends ReadWriteServiceImpl<Badge, Long> implements BadgeService {

    public BadgeServiceImpl(BadgeDao badgeDao) {
        super(badgeDao);
    }
}
