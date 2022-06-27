package com.stackexchange.featuredquestions.domain.repository;

import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedQuestionRepository extends JpaRepository<FeaturedQuestion, Integer>, JpaSpecificationExecutor<FeaturedQuestion> {
}
