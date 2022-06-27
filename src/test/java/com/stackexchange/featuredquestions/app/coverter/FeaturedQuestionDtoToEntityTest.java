package com.stackexchange.featuredquestions.app.coverter;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeaturedQuestionDtoToEntityTest {

    @InjectMocks
    private FeaturedQuestionDtoToEntityConverter converter;

    @Mock
    private Converter<String, QuestionTag> stringQuestionTagConverter;

    @Test
    public void testConvert() {
        List<String> tags = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<QuestionTag> questionTags = Mockito.mock(List.class);
        when(stringQuestionTagConverter.convertAll(eq(tags))).thenReturn(questionTags);
        FeaturedQuestionDto dto = new FeaturedQuestionDto();
        dto.setId(2);
        dto.setAnswered(false);
        dto.setTags(tags);
        dto.setAnswerCount(0);
        dto.setCreationDate(LocalDateTime.now());
        dto.setViewCount(0);
        dto.setUserId(1);
        FeaturedQuestion question = converter.convert(dto);
        assertNull(question.getId());
        assertEquals(dto.getAnswerCount(), question.getAnswerCount());
        assertEquals(dto.getAnswered(), question.getAnswered());
        assertEquals(questionTags, question.getTags());
        assertEquals(dto.getCreationDate(), question.getCreationDate());
        assertEquals(dto.getViewCount(), question.getViewCount());
        assertEquals(dto.getUserId(), question.getUserId());
    }

}