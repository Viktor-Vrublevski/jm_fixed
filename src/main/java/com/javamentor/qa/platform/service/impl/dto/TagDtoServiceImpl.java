package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDao;
    private final PaginationService<TagDto, Object> paginationServiceTagDto;
    private final PaginationService<TagRecentDto, Object> paginationServiceRecentTag;
    private final PaginationService<TagListDto, Object> paginationServiceTagListDto;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao,
                             PaginationService<TagDto, Object> paginationServiceTagDto,
                             PaginationService<TagRecentDto, Object> paginationServiceRecentTag,
                             PaginationService<TagListDto, Object> paginationServiceTagListDto) {
        this.tagDtoDao = tagDtoDao;
        this.paginationServiceTagDto = paginationServiceTagDto;
        this.paginationServiceRecentTag = paginationServiceRecentTag;
        this.paginationServiceTagListDto = paginationServiceTagListDto;
    }

    @Override
    public PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size) {
        return paginationServiceTagDto.getPageDto(
                "paginationTagsByPopular",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }


    @Override
    public PageDto<TagListDto, Object> getTagListDtoByPopularPagination(int page, int size) {
        return paginationServiceTagListDto.getPageDto(
                "paginationTagsByOrderPopular",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    @Override
    public PageDto<TagListDto, Object> getTagDtoPaginationOrderByAlphabet(int page, int size) {
        return paginationServiceTagListDto.getPageDto(
                "paginationTagsOrderByAlphabet",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    @Override
    public PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size) {
        return paginationServiceRecentTag.getPageDto(
                "paginationTagsRecent",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    @Override
    public PageDto<TagRecentDto, Object> getTagRecentDtoChildTagById(int page, int size, Long tagId) {
        return paginationServiceRecentTag.getPageDto(
                "paginationTagsRecentById",
                setPaginationParameters(page, size, Optional.empty(), Optional.ofNullable(tagId)));
    }


    @Override
    public PageDto<TagListDto, Object> getTagDtoPaginationWithSearch(int page, int size, String tagName) {
        return paginationServiceTagListDto.getPageDto(
                "paginationTagsWithNameSearch",
                setPaginationParameters(page, size, Optional.ofNullable(tagName), Optional.empty()));
    }

    @Override
    public PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size) {
        return paginationServiceTagListDto.getPageDto(
                "paginationTagsOrderByNewTag",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    private Map<String, Object> setPaginationParameters(int page, int size, Optional<String> name, Optional<Long> tagId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("tagName", name.orElse(""));
        parameters.put("tagId", tagId.orElse(0L));

        return parameters;
    }

    @Override
    public List<IgnoredTagDto> getIgnoredTagsByPrincipal(long id) {
        return tagDtoDao.getIgnoredTagsByPrincipal(id);
    }

    @Override
    public List<TrackedTagDto> getTrackedTagsByPrincipal(long id) {
        return tagDtoDao.getTrackedTagsByPrincipal(id);
    }

}
