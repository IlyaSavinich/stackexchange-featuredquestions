package com.stackexchange.featuredquestions.app.controller;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.service.FeaturedQuestionService;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/featured-questions")
@AllArgsConstructor
public class FeaturedQuestionController {

    private final FeaturedQuestionService questionService;
    private final Converter<FeaturedQuestion, FeaturedQuestionDto> featureQuestionToDtoConverter;

    @GetMapping
    public List<FeaturedQuestionDto> getFeaturedQuestions(
            @RequestParam(required = false) @Nullable String tag
    ) {
        val questions = questionService.getQuestions(tag);
        return featureQuestionToDtoConverter.convertAll(questions);
    }

    @GetMapping(path = "/{id}")
    public FeaturedQuestionDto getFeaturedQuestion(@PathVariable Integer id) {
        return featureQuestionToDtoConverter.convert(questionService.getById(id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeaturedQuestion(@PathVariable Integer id) {
        questionService.deleteById(id);
    }
}
