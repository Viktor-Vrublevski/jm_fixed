package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersDtoServiceImpl implements AnswerDtoService {

    private AnswerDtoDao answerDtoDao;

    @Autowired
    public AnswersDtoServiceImpl(AnswerDtoDao answerDtoDao){
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    public List<AnswerDto> getAllAnswersByQuestionId(Long questionId) {
        return answerDtoDao.getAllAnswersByQuestionId(questionId);
    }
}
