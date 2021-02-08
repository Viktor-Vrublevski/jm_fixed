package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.dto.CommentQuestionDto;

import java.util.List;

public interface CommentDtoDao {
    List<CommentQuestionDto> getAllCommentsByQuestionId(Long questionId);

    List<CommentAnswerDto> getAllCommentsByAnswerId(Long answerId);

}
