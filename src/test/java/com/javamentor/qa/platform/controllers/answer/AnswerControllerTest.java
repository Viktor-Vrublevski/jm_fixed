package com.javamentor.qa.platform.controllers.answer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.dto.CreateAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.webapp.converters.AnswerConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DataSet(value = {"" +
        "dataset/answer/usersApi.yml",
        "dataset/answer/answerApi.yml",
        "dataset/answer/roleApi.yml",
        "dataset/answer/questionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml"
       },
        cleanBefore = true, cleanAfter = false)
@WithMockUser(username = "principal@mail.ru", roles={"ADMIN", "USER"})
@ActiveProfiles("local")
class AnswerControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerConverter answerConverter;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void shouldAddCommentToAnswerResponseBadRequestAnswerNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/1/answer/99999/comment")
                .content("This is very good answer!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer not found"));
    }


    @Test
    public void shouldAddCommentToAnswerResponseCommentDto() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/4/answer/3/comment")
                .content("This is very good answer!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is very good answer!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentType").value("ANSWER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Teat"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2))
                .andReturn();

        JSONObject dto = new JSONObject(result.getResponse().getContentAsString());

        List<CommentAnswer> resultList = entityManager.createNativeQuery("select * from comment_answer where comment_id = " + dto.get("id")).getResultList();
        Assert.assertFalse(resultList.isEmpty());
    }

    @Test
    void shouldGetAnswersListFromQuestionStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/10/answer")
                .contentType("application/json;charset=UTF-8")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldAddAnswerToQuestionStatusOk() throws Exception {


        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/14/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddAnswerToQuestionResponseStatusOk() throws Exception {
        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        String resultContext = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/15/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(createAnswerDto.getHtmlBody()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andReturn().getResponse().getContentAsString();

        AnswerDto answerDtoFromResponse = objectMapper.readValue(resultContext, AnswerDto.class);
        Answer answer = entityManager
                .createQuery("from Answer where id = :id", Answer.class)
                .setParameter("id", answerDtoFromResponse.getId())
                .getSingleResult();
        AnswerDto answerDtoFromDB = answerConverter.answerToAnswerDTO(answer);

        Assert.assertTrue(answerDtoFromResponse.getBody().equals(answerDtoFromDB.getBody()));
    }


    @Test
    void shouldGetAnswersListFromQuestionResponseStatusOk() throws Exception {
        String resultContext = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/10/answer")
                .param("page", "1")
                .param("size", "10"))
                .andReturn().getResponse().getContentAsString();

        List<AnswerDto> answerDtoListFromResponse = objectMapper.readValue(resultContext, new TypeReference<List<AnswerDto>>(){});
        List<AnswerDto> answerList = (List<AnswerDto>) entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(a.id, u.id, q.id, " +
                        "a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                        "(SELECT COUNT(av.answer.id) FROM AnswerVote AS av WHERE av.answer.id = a.id), " +
                        "u.imageLink, u.fullName) " +
                        "FROM Answer as a " +
                        "INNER JOIN a.user as u " +
                        "JOIN a.question as q " +
                        "WHERE q.id = :questionId")
                .setParameter("questionId", 10L)
                .getResultList();

        Assert.assertTrue(answerDtoListFromResponse.equals(answerList));
    }

    @Test
    void shouldGetAnswersListFromQuestionResponseBadRequestQuestionNotFound() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/2222/answer")
                .contentType("application/json;charset=UTF-8")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question not found"));
    }


    @Test
    void shouldAddAnswerToQuestionResponseBadRequestQuestionNotFound() throws Exception {

        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/2222/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question not found"));
    }

    @Test
    void voteUpStatusOk() throws Exception {

        List<AnswerVote> before = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/51/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").isNumber())
                .andExpect(jsonPath("$.answerId").isNumber())
                .andExpect(jsonPath("$.persistDateTime").isNotEmpty())
                .andExpect(jsonPath("$.vote").isNumber());

        List<AnswerVote> after = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }

    @Test
    void voteUpQuestionIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/100/answer/13/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question was not found"));

    }

    @Test
    void voteUpAnswerIsNotExist() throws Exception {
///api/question/1/answer/4 = 5/upVote
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/answer/5/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer was not found"));
    }

    @Test
    void voteDownStatusOk() throws Exception {

        List<AnswerVote> before = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int first = before.size();
// /api/question/1/answer/14 = 3/downVote
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/answer/3/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").isNumber())
                .andExpect(jsonPath("$.answerId").isNumber())
                .andExpect(jsonPath("$.persistDateTime").isNotEmpty())
                .andExpect(jsonPath("$.vote").isNumber());

        List<AnswerVote> after = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }

    @Test
    void voteDownQuestionIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/100/answer/14/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question was not found"));

    }

    @Test
    void voteDownAnswerIsNotExist() throws Exception {
///api/question/1/answer/ 4 = 40/downVote
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/answer/40/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer was not found"));
    }

    @Test
    @WithMockUser(username = "admin@tut.by", roles = {"ADMIN"})
    public void shouldMarkAnswerAsHelpful() throws Exception {

        Answer beforeAnswer = (Answer) entityManager.createQuery("From Answer Where id=4").getSingleResult();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/4/upVote"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        JSONObject object = new JSONObject(result.getResponse().getContentAsString());

        Answer afterAnswer = (Answer) entityManager.createQuery("From Answer Where id=4").getSingleResult();

        Assert.assertFalse(beforeAnswer.getIsHelpful());
        Assert.assertEquals(object.get("userId"),4);
        Assert.assertTrue(afterAnswer.getIsHelpful());
    }


}