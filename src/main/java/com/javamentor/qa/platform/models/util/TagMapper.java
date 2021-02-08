package com.javamentor.qa.platform.models.util;

import com.javamentor.qa.platform.models.dto.TagRecentDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    List<TagRecentDto> tagToTagRecentDtos(List<Tag> tags);

    TagRecentDto tagToTagRecentDto(Tag tags);

}
