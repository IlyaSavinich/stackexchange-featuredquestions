package com.stackexchange.featuredquestions.domain.service;

import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import com.stackexchange.featuredquestions.domain.repository.FeaturedQuestionRepository;
import com.stackexchange.featuredquestions.domain.repository.QuestionTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.stackexchange.featuredquestions.domain.speficiation.FeaturedQuestionSpecification.hasTagWithName;

@Service
@AllArgsConstructor
public class FeaturedQuestionService {

    private final FeaturedQuestionRepository featuredQuestionRepository;
    private final QuestionTagRepository questionTagRepository;

    @Transactional
    public void createAll(Collection<FeaturedQuestion> featuredQuestions) {
        featuredQuestionRepository.saveAll(featuredQuestions);
        List<QuestionTag> tags = featuredQuestions.stream()
                                                  .peek(question ->
                                                          question.getTags().forEach((tag) -> tag.setQuestion(question))
                                                  )
                                                  .flatMap(question -> question.getTags().stream()).toList();
        questionTagRepository.saveAll(tags);
    }

    public FeaturedQuestion getById(Integer id) {
        return featuredQuestionRepository.findById(id).orElseThrow(() -> createEntityNotFoundException(id));
    }

    public List<FeaturedQuestion> getQuestions(@Nullable String tag) {
        Specification<FeaturedQuestion> questionSpecification = null;
        if (tag != null) {
            questionSpecification = hasTagWithName(tag);
        }
        return featuredQuestionRepository.findAll(questionSpecification);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!featuredQuestionRepository.existsById(id)) throw createEntityNotFoundException(id);
        featuredQuestionRepository.deleteById(id);
    }

    private EntityNotFoundException createEntityNotFoundException(Integer id) {
        return new EntityNotFoundException("Could not find featured question with id: " + id);
    }
}
