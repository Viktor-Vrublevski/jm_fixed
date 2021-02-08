package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDto implements Serializable {

    @NotNull(groups = OnCreate.class, message = "Значение title отсутствует")
    @NotBlank(groups = OnCreate.class, message = "Значение title не должно быть пустым")
    private String title;

    @NotNull(groups = OnCreate.class, message = "Значение userId должно быть заполнено")
    private Long userId;

    @NotNull(groups = OnCreate.class, message = "Значение description отсутствует")
    @NotBlank(groups = OnCreate.class, message = "Значение description не должно быть пустым")
    private String description;

    @NotNull(groups = OnCreate.class, message = "Значение tags должно быть заполнено")
    private List<TagDto> tags;

}


