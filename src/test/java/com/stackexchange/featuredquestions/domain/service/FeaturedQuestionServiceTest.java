package com.stackexchange.featuredquestions.domain.service;

import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import com.stackexchange.featuredquestions.domain.repository.FeaturedQuestionRepository;
import com.stackexchange.featuredquestions.domain.repository.QuestionTagRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeaturedQuestionServiceTest {

    @InjectMocks
    private FeaturedQuestionService featuredQuestionService;

    @Mock
    private FeaturedQuestionRepository featuredQuestionRepository;

    @Mock
    private QuestionTagRepository questionTagRepository;

    @Test
    public void testCreateAll() {
        val featuredQuestion = Mockito.mock(FeaturedQuestion.class);
        val featuredQuestions = List.of(featuredQuestion);
        when(featuredQuestionRepository.saveAll(eq(featuredQuestions))).thenReturn(featuredQuestions);
        val questionTag = Mockito.mock(QuestionTag.class);
        val questionTags = List.of(questionTag);
        when(featuredQuestion.getTags()).thenReturn(questionTags);
        featuredQuestionService.createAll(featuredQuestions);
        verify(featuredQuestionRepository, times(1)).saveAll(eq(featuredQuestions));
        verify(questionTag, times(1)).setQuestion(eq(featuredQuestion));
        verify(questionTagRepository, times(1)).saveAll(eq(questionTags));
    }

    @Test
    public void testGetByIdWhenExists() {
        val id = 32;
        val featuredQuestion = Mockito.mock(FeaturedQuestion.class);
        val featuredQuestionOptional = Optional.of(featuredQuestion);
        when(featuredQuestionRepository.findById(eq(id))).thenReturn(featuredQuestionOptional);
        val featuredQuestionById = featuredQuestionService.getById(id);
        assertEquals(featuredQuestionById, featuredQuestion);
    }

    @Test
    public void testGetByIdWhenNotExists() {
        val id = 32;
        Optional<FeaturedQuestion> featuredQuestionOptional = Optional.empty();
        when(featuredQuestionRepository.findById(eq(id))).thenReturn(featuredQuestionOptional);
        assertThrows(EntityNotFoundException.class, () -> featuredQuestionService.getById(id));
    }

    @Test
    public void testGetQuestions() {
        val featuredQuestions = List.of(Mockito.mock(FeaturedQuestion.class));
        when(featuredQuestionRepository.findAll(any(Specification.class))).thenReturn(featuredQuestions);
        assertEquals(featuredQuestionService.getQuestions("any string"), featuredQuestions);
    }

    @Test
    public void testDeleteByIdWhenExists() {
        val id = 32;
        when(featuredQuestionRepository.existsById(eq(id))).thenReturn(true);
        featuredQuestionService.deleteById(id);
        verify(featuredQuestionRepository, times(1)).deleteById(eq(id));
    }

    @Test
    public void testDeleteByIdWhenNotExists() {
        val id = 32;
        when(featuredQuestionRepository.existsById(eq(id))).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> featuredQuestionService.deleteById(id));
    }

}