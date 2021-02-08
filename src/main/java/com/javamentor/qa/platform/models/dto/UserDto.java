package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String linkImage;
    private int reputation;
}
