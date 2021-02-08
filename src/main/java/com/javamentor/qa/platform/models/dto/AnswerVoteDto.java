package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVoteDto implements Serializable {

    private Long id;
    private Long userId;
    private Long answerId;
    private LocalDateTime persistDateTime;
    private int vote;
}
