package com.javamentor.qa.platform.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDto {

    private long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String role;
}
