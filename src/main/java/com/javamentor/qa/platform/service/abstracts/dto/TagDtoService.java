package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.*;
import java.util.List;


public interface TagDtoService {
    PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size);

    PageDto<TagListDto, Object> getTagDtoPaginationOrderByAlphabet(int page, int size);

    PageDto<TagListDto, Object> getTagListDtoByPopularPagination(int page, int size);

    PageDto<TagListDto, Object> getTagDtoPaginationWithSearch(int page, int size, String tagName);

    PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size);

    PageDto<TagRecentDto, Object> getTagRecentDtoChildTagById(int page, int size, Long tagId);

    PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size);

    List<IgnoredTagDto> getIgnoredTagsByPrincipal(long id);

    List<TrackedTagDto> getTrackedTagsByPrincipal(long id);
}
