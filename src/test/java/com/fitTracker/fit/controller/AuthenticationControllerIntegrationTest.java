package com.fitTracker.fit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitTracker.fit.context.TestContext;
import com.fitTracker.fit.dto.requestDto.user.LoginDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationControllerIntegrationTest extends TestContext {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final JsonUtil jsonUtil;

    @Test
    public void loginWithBadCredentials() throws Exception {
        //given
        final String url = "/api/v1/login";

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(jsonUtil.getJsonFileString("invalid_login.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    //given
    public void loginWithCorrectCredentials() throws Exception {
        final String url = "/api/v1/login";
        final AuthResultDto expectedResult = jsonUtil.deserialize("correct_login_response.json", AuthResultDto.class, true);
        LoginDto loginDto = LoginDto.builder()
                .email("tester3@gmail.com")
                .password("Aa12345")
                .build();

//        ResultActions resultU = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/"));
//        String resultUs = resultU.andReturn().getResponse().getContentAsString();
        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(jsonUtil.serialize(loginDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        AuthResultDto responseResult = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), AuthResultDto.class);

        //then
        assertThat(responseResult)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedResult);
    }
}