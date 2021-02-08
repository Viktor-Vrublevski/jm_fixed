package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackedTagDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
}
