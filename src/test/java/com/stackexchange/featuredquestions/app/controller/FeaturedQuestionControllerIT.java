package com.stackexchange.featuredquestions.app.controller;

import com.github.tomakehurst.wiremock.http.MimeType;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeaturedQuestionControllerIT extends BaseHttpIT {

    @Override
    protected void setupStubs() {
        stubFor(get(urlPathMatching("/questions/featured.*")).willReturn(okForContentType(
                MimeType.JSON.toString(),
                readResource("/integrationtest/featuredquestions-response.json")
        )));
    }

    @Test
    public void givenTwentyFeaturedQuestions_whenGetFeaturedQuestionsWithoutTagSpefified_thenReturnAllQuestions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/featured-questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(20)));
    }

    @Test
    public void givenTwentyFeaturedQuestions_whenGetFeaturedQuestionsTagSpecified_thenReturnQuestionsWithFollowingTag() throws Exception {
        val tags = List.of("javascript", "http", "html", "python");
        for (String tag : tags) {
            testWithTag(tag);
        }
    }

    @Test
    @Transactional
    public void givenTwentyFeaturedQuestions_whenDeleteFeaturedQuestionById_thenNoContent() throws Exception {
        val integerList = IntStream.range(1, 21).boxed().toList();
        for (Integer id : integerList) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/featured-questions/" + id))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    @Transactional
    public void givenTwentyFeaturedQuestions_whenDeleteFeaturedQuestionByWrongId_thenReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/featured-questions/" + 23))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenTwentyFeaturedQuestions_whenGetFeaturedQuestionById_thenReturnQuestion() throws Exception {
        val integerList = IntStream.range(1, 21).boxed().toList();
        for (Integer id : integerList) {
            mockMvc.perform(MockMvcRequestBuilders.get("/featured-questions/" + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id)));
        }
    }

    @Test
    public void givenTwentyFeaturedQuestions_whenGetFeaturedQuestionByWrongId_thenReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/featured-questions/" + 23))
                .andExpect(status().isNotFound());
    }

    private void testWithTag(String tag) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/featured-questions?tag=" + tag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].tags.*", hasItem(tag)));
    }

}