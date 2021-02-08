package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class TagTrackedConverter {


    @Mapping(source = "trackedTag.id", target = "id")
    @Mapping(source = "trackedTag.name", target = "name")
    public abstract TrackedTagDto trackedTagToTrackedTagDto(TrackedTag trackedTag);

}
