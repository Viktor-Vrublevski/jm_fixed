package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentConverter {

    @Mapping(source = "comment", target = ".")
    @Mapping(source = "comment.persistDateTime", target = "persistDate")
    @Mapping(source = "comment.lastUpdateDateTime", target = "lastRedactionDate")
    @Mapping(source = "comment.user.id", target = "userId")
    @Mapping(source = "comment.user.fullName", target = "username")
    @Mapping(source = "comment.user.reputationCount", target = "reputation")
    public abstract CommentDto commentToCommentDTO(CommentQuestion commentQuestion);

    @Mapping(source = "comment", target = ".")
    @Mapping(source = "comment.persistDateTime", target = "persistDate")
    @Mapping(source = "comment.lastUpdateDateTime", target = "lastRedactionDate")
    @Mapping(source = "comment.user.id", target = "userId")
    @Mapping(source = "comment.user.fullName", target = "username")
    @Mapping(source = "comment.user.reputationCount", target = "reputation")
    public abstract CommentDto commentToCommentDTO(CommentAnswer commentAnswer);
}
