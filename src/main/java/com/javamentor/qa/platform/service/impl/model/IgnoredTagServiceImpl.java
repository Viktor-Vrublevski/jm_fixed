package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {

    private final IgnoredTagDao ignoredTagDao;

    public IgnoredTagServiceImpl(IgnoredTagDao ignoredTagDao){
        super(ignoredTagDao);
        this.ignoredTagDao = ignoredTagDao;
    }

    @Override
    public List<IgnoredTag> getIgnoredTagsByUser(User user) {
        return ignoredTagDao.getIgnoredTagsByUser(user.getUsername());
    }

    @Override
    public List<User> getUsersByIgnoredTag(Tag tag) {
        return null;
    }

    @Override
    public void addIgnoredTagsToUser(List<Tag> tagList, User user) {
        for (Tag tag: tagList) {
            IgnoredTag ignoredTag = new IgnoredTag();
            ignoredTag.setIgnoredTag(tag);
            ignoredTag.setUser(user);
            ignoredTagDao.addIgnoredTag(ignoredTag);
        }

    }

    @Override
    public Optional<IgnoredTag> getIgnoredTagDtoByName(Long id, String name){
        return ignoredTagDao.getIgnoredTagDtoByName(id, name);
    }

    @Override
    public void deleteIgnoredTagByIdTagIdUser(Long id, Long tagId) {
        ignoredTagDao.deleteIgnoredTagByIdTagIdUser(id, tagId);
    }
}
