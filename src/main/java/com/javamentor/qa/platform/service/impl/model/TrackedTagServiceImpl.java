package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDao trackedTagDao;

    public TrackedTagServiceImpl(TrackedTagDao trackedTagDao){
        super(trackedTagDao);
        this.trackedTagDao = trackedTagDao;
    }

    @Override
    public List<TrackedTag> getTrackedTagsByUser(User user) {
        return trackedTagDao.getTrackedTagsByUser(user.getUsername());
    }

    @Override
    public List<User> getUsersByTrackedTag(Tag tag) {
        return null;
    }

    @Override
    public void addTrackedTagsToUser(List<Tag> tagList, User user) {
        for (Tag tag: tagList) {
            TrackedTag trackedTag = new TrackedTag();
            trackedTag.setTrackedTag(tag);
            trackedTag.setUser(user);
            trackedTagDao.addTrackedTag(trackedTag);
        }

    }

    @Override
    public Optional<TrackedTag> getTrackedTagDtoByName(Long id, String name){
        return trackedTagDao.getTrackedTagDtoByName(id, name);
    }

    @Override
    public void deleteTrackedTagByIdTagIdUser(Long id, Long tagId) {
        trackedTagDao.deleteTrackedTagByIdTagIdUser(id, tagId);
    }
}
