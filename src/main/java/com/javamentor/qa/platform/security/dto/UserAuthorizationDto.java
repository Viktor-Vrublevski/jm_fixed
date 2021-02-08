package com.javamentor.qa.platform.security.dto;

import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorizationDto {

    @NotNull(groups = OnCreate.class, message = "Username not by null")
    @NotBlank(groups = OnCreate.class, message = "Username not by empty")
    private String username;

    @NotNull(groups = OnCreate.class, message = "Username not by null")
    @NotBlank(groups = OnCreate.class, message = "Username not by empty")
    private String password;
}
