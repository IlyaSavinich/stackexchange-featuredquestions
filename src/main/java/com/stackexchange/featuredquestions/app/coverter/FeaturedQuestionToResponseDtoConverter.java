package com.stackexchange.featuredquestions.app.coverter;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FeaturedQuestionToResponseDtoConverter implements Converter<FeaturedQuestion, FeaturedQuestionDto> {
    @Override
    public FeaturedQuestionDto convert(FeaturedQuestion featuredQuestion) {
        val featuredQuestionResponseDto = new FeaturedQuestionDto();
        featuredQuestionResponseDto.setId(featuredQuestion.getId());
        featuredQuestionResponseDto.setAnswered(featuredQuestion.getAnswered());
        featuredQuestionResponseDto.setAnswerCount(featuredQuestion.getAnswerCount());
        featuredQuestionResponseDto.setUserId(featuredQuestion.getUserId());
        featuredQuestionResponseDto.setCreationDate(featuredQuestion.getCreationDate());
        featuredQuestionResponseDto.setViewCount(featuredQuestion.getViewCount());
        featuredQuestionResponseDto.setTags(
                featuredQuestion.getTags()
                                .stream()
                                .map(QuestionTag::getName)
                                .collect(Collectors.toList())
        );
        return featuredQuestionResponseDto;
    }
}
