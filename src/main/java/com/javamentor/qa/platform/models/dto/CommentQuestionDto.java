package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentQuestionDto implements Serializable {
private Long id;
private Long questionId;
private LocalDateTime lastRedactionDate;
private LocalDateTime persistDate;
private String text;
private Long userId;
}
