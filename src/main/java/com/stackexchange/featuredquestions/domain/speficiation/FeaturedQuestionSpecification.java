package com.stackexchange.featuredquestions.domain.speficiation;

import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class FeaturedQuestionSpecification {
    public static Specification<FeaturedQuestion> hasTagWithName(String tagName) {
        return (root, query, criteriaBuilder) -> {
            Join<QuestionTag, FeaturedQuestion> questionTags = root.join("tags");
            return criteriaBuilder.equal(questionTags.get("name"), tagName);
        };
    }
}
