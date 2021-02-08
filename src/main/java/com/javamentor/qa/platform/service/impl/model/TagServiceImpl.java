package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {


    private final TagDao tagDao;
    private final QuestionDao questionDao;

    public TagServiceImpl(TagDao tagDao, QuestionDao questionDao) {
        super(tagDao);
        this.questionDao = questionDao;
        this.tagDao = tagDao;
    }

    @Override
    public Optional<Tag> getById(Long id) {
        return tagDao.getById(id);
    }


    @Transactional
    @Override
    public void addTagToQuestion(List<Long> tagId, Question question){


        for(Long id : tagId){
            Tag tag = getById(id).get();


            List<Tag> listTagQuestion= questionDao.getAllTagOfQuestion(question);
            if(!listTagQuestion.contains(tag)) {
                listTagQuestion.add(tag);
            }
            question.setTags(listTagQuestion);
        }

    }

    @Override
    public Optional<Tag> getTagByName(String name){
        return tagDao.getTagByName(name);
    }
}
