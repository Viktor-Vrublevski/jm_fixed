package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.dto.CommentQuestionDto;

import java.util.List;

public interface CommentDtoService {

    List<CommentQuestionDto> getAllCommentsByQuestionId(Long questionId);

    List<CommentAnswerDto> getAllCommentsByAnswerId(Long questionId);


}
