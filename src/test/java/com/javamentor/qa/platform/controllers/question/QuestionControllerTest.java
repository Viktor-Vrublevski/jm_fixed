package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml",
        "dataset/question/votes_on_question.yml"},
        useSequenceFiltering = true, cleanBefore = true, cleanAfter = false)
@WithMockUser(username = "principal@mail.ru", roles = {"ADMIN", "USER"})
@ActiveProfiles("local")
class QuestionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void getAllDto() throws Exception {
        System.out.println("test");
    }

    @Test
    public void shouldSetTagForQuestionOneTag() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(new Long(1L));
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSetTagForQuestionWrongId() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(new Long(1L));
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1111/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Question not found"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldSetTagForQuestionFewTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(new Long(1L));
        tag.add(new Long(2L));
        tag.add(new Long(3L));
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldSetTagForQuestionNoTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(new Long(11L));
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tag not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorMessageBadParameterWrongSizeQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterWrongPageQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "0")
                .param("size", "2"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    void shouldAddQuestionStatusOk() throws Exception {

        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(2L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void shouldAddQuestionAnswerStatusOk() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(1L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Question number one1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Question Description493"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId").value(1));
    }


    @Test
    public void shouldAddQuestionResponseBadRequestUserNotFound() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(2222L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("questionCreateDto.userId dont`t exist"));
    }

    @Test
    public void shouldAddQuestionResponseBadRequestTagsNotExist() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(1L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        questionCreateDto.setTags(null);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("addQuestion.questionCreateDto.tags: Значение tags должно быть заполнено"));
    }

    @Test
    public void shouldReturnQuestionsWithGivenTags() throws Exception {
        Long a[] = {1L, 3L, 5L};
        List<Long> tagIds = Arrays.stream(a).collect(Collectors.toList());
        String jsonRequest = objectMapper.writeValueAsString(tagIds);

        String resultContext = mockMvc.perform(MockMvcRequestBuilders.get("/api/question/withTags")
                .content(jsonRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "3")
                .param("tagIds", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPageCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResultCount").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemsOnPage").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        PageDto<LinkedHashMap, Object> actual = objectMapper.readValue(resultContext, PageDto.class);

        int numberOfItemsOnPage = actual.getItems().size();
        Assert.assertTrue(numberOfItemsOnPage == 3);
    }

    @Test
    public void getQuestionSearchWithStatusOk() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("sql query in excel");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuestionSearchWithSearchMessageNull() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto(null);
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getQuestionSearchWithSearchMessageIsEmpty() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getQuestionSearchWithSearchByAuthor() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("author:3");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        PageDto<QuestionDto, Object> expect = new PageDto<>();
        expect.setTotalResultCount(2);
        expect.setItemsOnPage(1);
        expect.setCurrentPageNumber(1);
        expect.setTotalPageCount(2);
        expect.setItemsOnPage(1);
        expect.setMeta(null);

        List<TagDto> tag = new ArrayList<>();
        tag.add(new TagDto(5L, "sql"));

        List<QuestionDto> items = new ArrayList<>();
        items.add(new QuestionDto(
                18L,
                "Question number seven",
                3L, "Tot", null,
                "Changes made in sql query in excel reflects changes only on single sheet",
                1, 3, 1,
                LocalDateTime.of(2020, 01, 02, 00, 00, 00),
                LocalDateTime.of(2020, 05, 02, 13, 58, 56), tag));

        expect.setItems(items);

        String jsonResult = objectMapper.writeValueAsString(expect);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResult));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterMaxPageQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "2")
                .param("size", "200"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void testPaginationQuestionsWithoutAnswer() throws Exception {

        this.mockMvc.perform(get("/api/question/withoutAnswer")
                .param("page", "1")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPageCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResultCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray())
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/question/usersQuestionApi.yml",
            "dataset/question/answerQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml",
            "dataset/question/votes_on_question.yml"},
            useSequenceFiltering = true, cleanBefore = true, cleanAfter = true)
    public void testIsQuestionWithoutAnswers() throws Exception {

        LocalDateTime persistDateTime = LocalDateTime.of(LocalDate.of(2020, 1, 2), LocalTime.of(0, 0, 0));
        LocalDateTime lastUpdateDateTime = LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56));

        PageDto<QuestionDto, Object> expectPage = new PageDto<>();
        expectPage.setCurrentPageNumber(1);
        expectPage.setItemsOnPage(1);
        expectPage.setTotalPageCount(7);
        expectPage.setTotalResultCount(7);

        List<TagDto> tagsList = new ArrayList<>();
        tagsList.add(new TagDto(1L, "java"));

        List<QuestionDto> itemsList = new ArrayList<>();
        itemsList.add(new QuestionDto(14L,
                "Question number three",
                2L,
                "Tot",
                null,
                "Swagger - add \"path variable\" in request url",
                2,
                3,
                1,
                persistDateTime,
                lastUpdateDateTime,
                tagsList));

        expectPage.setItems(itemsList);

        String result = mockMvc.perform(get("/api/question/withoutAnswer")
                .param("page", "1")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageDto<QuestionDto, Object> actualPage = objectMapper.readValue(result, PageDto.class);
        Assert.assertEquals(expectPage.toString(), actualPage.toString());
    }

    @Test
    public void shouldReturnQuestionsWithoutSpecifiedTags() throws Exception {

        List<Long> withoutTagIds = new ArrayList<>();
        withoutTagIds.add(1L);
        withoutTagIds.add(2L);
        withoutTagIds.add(5L);

        String jsonWithoutTagIds = objectMapper.writeValueAsString(withoutTagIds);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/withoutTags")
                .param("page", "1")
                .param("size", "10")
                .contentType("application/json;charset=UTF-8")
                .content(jsonWithoutTagIds))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.totalResultCount").value(1))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.hasItem(3)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(2)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(5)))
                .andExpect(jsonPath("$.itemsOnPage").value(10));
    }

    @Test
    public void shouldReturnEmptyPaginationIfTagIdsMissing() throws Exception {

        List<Long> withoutTagIds = new ArrayList<>();

        String jsonWithoutTagIds = objectMapper.writeValueAsString(withoutTagIds);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/withoutTags")
                .param("page", "1")
                .param("size", "10")
                .contentType("application/json;charset=UTF-8")
                .content(jsonWithoutTagIds))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(0))
                .andExpect(jsonPath("$.totalResultCount").value(0))
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.itemsOnPage").value(10));
    }

    @Test
    public void shouldAddCommentToQuestionResponseBadRequestQuestionNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/9999/comment")
                .content("This is very good question!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }

    @Test
    public void shouldAddCommentToQuestionResponseCommentDto() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/10/comment")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is very good question!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentType").value("QUESTION"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(153))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Uou"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2))
                .andReturn();

        JSONObject dto = new JSONObject(result.getResponse().getContentAsString());

        List<CommentQuestion> resultList = entityManager.createNativeQuery("select * from comment_question where comment_id = " + dto.get("id")).getResultList();
        Assert.assertFalse(resultList.isEmpty());
    }
}