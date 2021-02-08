package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnUpdate;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPasswordDto {

    @NotNull(groups = OnUpdate.class, message = "Поле не должно быть null")
    @NotBlank(groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String oldPassword;

    @NotNull(groups = OnUpdate.class, message = "Поле не должно быть null")
    @NotBlank(groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String newPassword;
}
