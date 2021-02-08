package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IgnoredTagDto implements Serializable {

    private long id;
    private String name;
}
