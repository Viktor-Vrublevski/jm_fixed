package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto  implements Serializable {
    @NotNull(groups = OnCreate.class, message = "Значение fullName должно быть заполнено")
    public String fullName;

    @NotNull(groups = OnCreate.class, message = "Значение должно быть заполнено")
    @Email(groups = OnCreate.class, message = "Заданный email не может существовать")
    public String email;

    @NotNull(groups = OnCreate.class, message = "Значение должно быть заполнено")
    public String password;

}
