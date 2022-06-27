package com.stackexchange.featuredquestions.app.coverter;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeaturedQuestionDtoToEntityConverter implements Converter<FeaturedQuestionDto, FeaturedQuestion> {

    private final Converter<String, QuestionTag> stringToTagConverter;

    @Override
    public FeaturedQuestion convert(FeaturedQuestionDto featuredQuestionResponseDto) {
        val featuredQuestion = new FeaturedQuestion();
        featuredQuestion.setAnswered(featuredQuestionResponseDto.getAnswered());
        featuredQuestion.setAnswerCount(featuredQuestionResponseDto.getAnswerCount());
        featuredQuestion.setCreationDate(featuredQuestionResponseDto.getCreationDate());
        featuredQuestion.setUserId(featuredQuestionResponseDto.getUserId());
        featuredQuestion.setViewCount(featuredQuestionResponseDto.getViewCount());
        featuredQuestion.setTags(stringToTagConverter.convertAll(featuredQuestionResponseDto.getTags()));
        return featuredQuestion;
    }
}
