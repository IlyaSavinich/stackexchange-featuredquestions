package com.stackexchange.featuredquestions.domain.repository;

import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Integer> {
}
