package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long id;
    private String text;
    private LocalDateTime persistDate;
    private LocalDateTime lastRedactionDate;
    private CommentType commentType;
    private Long userId;
    private String username;
    private Long reputation;
}
