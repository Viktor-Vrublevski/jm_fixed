package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagRecentDto implements Serializable {
    private long id;
    private String name;
    private long countTagToQuestion;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=" + name +
                ", countTagToQuestion=" + countTagToQuestion +
                '}';
    }
}
