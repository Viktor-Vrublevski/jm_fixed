package com.javamentor.qa.platform.controllers.Authentication;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.security.dto.UserAuthorizationDto;
import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"dataset/authentication/roleAuthenticationApi.yml",
        "dataset/authentication/usersAuthenticationApi.yml"},
        useSequenceFiltering = true, cleanBefore = true, cleanAfter = false)
@ActiveProfiles("local")
class AuthenticationControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTokenStatusOk() throws Exception{
        UserAuthorizationDto user = new UserAuthorizationDto("Test1@mail.ru",
                "password0");
        String jsonRequest = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/token")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andReturn();


        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        String token = json.getString("jwtToken");

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/auth/authenticated")
                .header("authorization", "Bearer_" + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getTokenWrongPassword() throws Exception{
        UserAuthorizationDto user = new UserAuthorizationDto("Test1@mail.ru",
                "password");
        String jsonRequest = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/token")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getTokenWrongUserName() throws Exception{
        UserAuthorizationDto user = new UserAuthorizationDto("est1@mail.ru",
                "password0");
        String jsonRequest = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/token")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "Test1@mail.ru", roles={"ADMIN", "USER"})
    void getPrincipalUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/auth/principal"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Test1@mail.ru", roles={"ADMIN", "USER"})
    void auntheticatedCheckStatucOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/auth/authenticated"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void auntheticatedCheckIsNotAuth() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/auth/authenticated"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Error: User is not Authenticated"));
    }
}