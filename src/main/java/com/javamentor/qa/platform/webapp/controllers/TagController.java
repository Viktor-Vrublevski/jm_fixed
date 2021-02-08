package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.TagIgnoredConverter;
import com.javamentor.qa.platform.webapp.converters.TagTrackedConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/tag/")
@Api(value = "TagApi")
public class TagController {

    private final TagDtoService tagDtoService;
    private final SecurityHelper securityHelper;
    private final TrackedTagService trackedTagService;
    private final IgnoredTagService ignoredTagService;
    private final TagService tagService;

    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public TagController(TagDtoService tagDtoService, SecurityHelper securityHelper,
                         TrackedTagService trackedTagService, IgnoredTagService ignoredTagService, TagService tagService) {
        this.tagDtoService = tagDtoService;
        this.securityHelper = securityHelper;
        this.trackedTagService = trackedTagService;
        this.ignoredTagService = ignoredTagService;
        this.tagService = tagService;
    }

    @Autowired
    public TagTrackedConverter tagTrackedConverter;

    @Autowired
    public TagIgnoredConverter tagIgnoredConverter;

    @GetMapping("popular")
    @ApiOperation(value = "get page TagDto by popular. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by popular", response = List.class),
    })
    public ResponseEntity<?> getTagDtoPaginationByPopular(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagDto, Object> resultPage = tagDtoService.getTagDtoPaginationByPopular(page, size);

        return ResponseEntity.ok(resultPage);

    }


    @GetMapping("alphabet/order")
    @ApiOperation(value = "get page TagDto by alphabet. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by alphabet", response = List.class),
    })
    public ResponseEntity<?> getTagDtoPaginationOrderByAlphabet(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagListDto, Object> resultPage = tagDtoService.getTagDtoPaginationOrderByAlphabet(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping("order/popular")
    @ApiOperation(value = "get page TagListDto by popular. MAX SIZE ENTRIES ON PAGE=100")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagListDto> by popular"),
    })
    public ResponseEntity<?> getTagListDtoByPopularPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagListDto, Object> resultPage = tagDtoService.getTagListDtoByPopularPagination(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping("new/order")
    @ApiOperation(value = "Get page TagListDto by new tags. MAX SIZE ENTRIES ON PAGE = 100", response = String.class)
    public ResponseEntity<?> getTagListDtoPaginationOrderByNewTag(
            @ApiParam(name = "page", value = "Number page. Type int.", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page. Type int." +
                    "Recommended number of items per page " + MAX_ITEMS_ON_PAGE, example = "10")
            @RequestParam("size") int size) {
        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Page and Size have to be positive. " +
                    "Max number of items per page " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<TagListDto, Object> pageDto = tagDtoService.getTagListDtoPaginationOrderByNewTag(page, size);

        return ResponseEntity.ok(pageDto);

    }


    @GetMapping("recent")
    @ApiOperation(value = "get page TagRecentDto. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagRecentDto>", response = List.class),
    })
    public ResponseEntity<?> getTagRecentDtoPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagRecentDto, Object> resultPage = tagDtoService.getTagRecentDtoPagination(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping(value = "name", params = {"page", "size", "name"})
    @ApiOperation(value = "get page TagListDto with search by tag name. MAX SIZE ENTRIES ON PAGE=100", response = PageDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination PageDto<TagListDto> with search by tag name", response = PageDto.class),
    })
    public ResponseEntity<?> getTagName(
            @ApiParam(name = "page", value = "Number Page. Type int",
                    example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "name", value = "Incomplete tag name",
                    example = "tag")
            @RequestParam("name") String tagName,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Maximum number of records per page -" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть положительными. " +
                    "Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        return ResponseEntity.ok(tagDtoService.getTagDtoPaginationWithSearch(page, size, tagName));
    }

    @GetMapping(value = "{tagId}/child", params = {"page", "size"})
    @ApiOperation(value = "get page TagRecentDto with child tags by tag id. The results are sorted by popularity. MAX SIZE ENTRIES ON PAGE=100", response = PageDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination PageDto<TagRecentDto> with child tags by tag id", response = PageDto.class),
            @ApiResponse(code = 400, message = "Wrong ID or page number", response = String.class)
    })
    public ResponseEntity<?> getChildTagsById(
            @ApiParam(name = "page", value = "Number Page. Type int",
                    example = "10")
            @RequestParam("page") int page,
            @PathVariable Long tagId,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Maximum number of records per page -" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {
        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<TagRecentDto, Object> resultPage = tagDtoService.getTagRecentDtoChildTagById(page, size, tagId);

        return ResponseEntity.ok(resultPage);
    }

    @GetMapping(value = "ignored")
    @ApiOperation(value = "get list to ignored tags", response = TagDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the List<IgnoredTagDto> list", response = TagDto.class)
    })
    public ResponseEntity<?> getUserIgnoredTags() {
        List<IgnoredTagDto> tags =
                tagDtoService.getIgnoredTagsByPrincipal(securityHelper.getPrincipal().getId());
        return ResponseEntity.ok(tags);
    }

    @GetMapping(value = "tracked")
    @ApiOperation(value = "get list to tracked tags", response = TagDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the List<TrackedTagDto> list", response = TagDto.class)
    })
    public ResponseEntity<?> getUserTrackedTags() {
        List<TrackedTagDto> tags =
                tagDtoService.getTrackedTagsByPrincipal(securityHelper.getPrincipal().getId());
        return ResponseEntity.ok(tags);
    }

    @PostMapping(value = "tracked/add")
    @ApiOperation(value = "add trackedTags", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "successfully added a tracked tag", response = TrackedTagDto.class)
    })
    public ResponseEntity<?> addTrackedTagsList(@Valid @RequestParam String name) {
        Optional<Tag> createTag = tagService.getTagByName(name);
        if (!createTag.isPresent()) {
            return ResponseEntity.badRequest().body("The " + name + " does not exist on this site");
        }
        if (trackedTagService.getTrackedTagDtoByName(securityHelper.getPrincipal().getId(), name).isPresent()) {
            return ResponseEntity.badRequest().body("The tracked tag has already been added");
        }
        TrackedTag createTrackedTag = new TrackedTag();
        createTrackedTag.setUser(securityHelper.getPrincipal());
        createTrackedTag.setTrackedTag(createTag.get());
        trackedTagService.persist(createTrackedTag);
        TrackedTagDto creatTagDtoNew = tagTrackedConverter.trackedTagToTrackedTagDto(createTrackedTag);
        return ResponseEntity.ok(creatTagDtoNew);
    }

    @PostMapping(value = "ignored/add")
    @ApiOperation(value = "add ignoredTags", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "successfully added a ignored tag", response = IgnoredTagDto.class)
    })
    public ResponseEntity<?> addIgnoredTagsList(@Valid @RequestParam String name) {
        Optional<Tag> createTag = tagService.getTagByName(name);
        if (!createTag.isPresent()) {
            return ResponseEntity.badRequest().body("The " + name + " does not exist on this site");
        }
        if (ignoredTagService.getIgnoredTagDtoByName(securityHelper.getPrincipal().getId(), name).isPresent()) {
            return ResponseEntity.badRequest().body("The ignored tag has already been added");
        }
        IgnoredTag createIgnoredTag = new IgnoredTag();
        createIgnoredTag.setUser(securityHelper.getPrincipal());
        createIgnoredTag.setIgnoredTag(createTag.get());
        ignoredTagService.persist(createIgnoredTag);
        IgnoredTagDto creatTagDtoNew = tagIgnoredConverter.trackedTagToIgnoredTagDto(createIgnoredTag);
        return ResponseEntity.ok(creatTagDtoNew);
    }

    @DeleteMapping(value = "tracked/delete")
    @ApiOperation(value = "Delete Tracked tag by id", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tracked tag was deleted.", response = TrackedTagDto.class),
    })
    public ResponseEntity<?> deleteTrackedTag(@Valid @RequestParam Long tagId) {

        trackedTagService.deleteTrackedTagByIdTagIdUser(securityHelper.getPrincipal().getId(),tagId);
        return ResponseEntity.ok("Tracked Tag with ID = " + tagId + " was deleted");
    }



    @DeleteMapping(value = "ignored/delete")
    @ApiOperation(value = "Delete Ignored tag by id", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ignored tag was deleted.", response = IgnoredTagDto.class),
    })
    public ResponseEntity<?> deleteIgnoredTag(@Valid @RequestParam Long tagId) {

        ignoredTagService.deleteIgnoredTagByIdTagIdUser(securityHelper.getPrincipal().getId(),tagId);
        return ResponseEntity.ok("Ignored Tag with ID = " + tagId + " was deleted");
    }
}





