package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.RelatedTagDao;
import com.javamentor.qa.platform.models.entity.question.RelatedTag;
import com.javamentor.qa.platform.service.abstracts.model.RelatedTagService;
import org.springframework.stereotype.Service;

@Service
public class RelatedTagServiceImpl extends ReadWriteServiceImpl<RelatedTag, Long> implements RelatedTagService {
    public RelatedTagServiceImpl(RelatedTagDao relatedTagDao) {
        super(relatedTagDao);
    }
}
