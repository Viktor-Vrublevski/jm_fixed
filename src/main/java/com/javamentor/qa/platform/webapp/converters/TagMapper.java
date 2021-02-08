package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring", uses = Tag.class)
public abstract class TagMapper {

     public abstract List<Tag> dtoToTag (List<TagDto> tagDto);
}
