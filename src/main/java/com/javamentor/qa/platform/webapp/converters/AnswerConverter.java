package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public abstract class AnswerConverter {

    @Mapping(source = "answer.id", target = "id")
    @Mapping(source = "answer.user.id", target = "userId")
    @Mapping(source = "answer.question.id", target = "questionId")
    @Mapping(source = "answer.htmlBody", target = "body")
    @Mapping(source = "answer.persistDateTime", target = "persistDate")
    @Mapping(source = "answer.isHelpful", target = "isHelpful")
    @Mapping(source = "answer.dateAcceptTime", target = "dateAccept")
    @Mapping(target = "countValuable", constant = "0L")
    @Mapping(source = "answer.user.imageLink", target = "image")
    @Mapping(source = "answer.user.nickname", target = "nickName")
    public abstract AnswerDto answerToAnswerDTO(Answer answer);

}