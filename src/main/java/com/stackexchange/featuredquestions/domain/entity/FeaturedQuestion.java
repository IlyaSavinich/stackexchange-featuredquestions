package com.stackexchange.featuredquestions.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "featured_question")
@Entity
@Data
@NoArgsConstructor
public class FeaturedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<QuestionTag> tags;

    @Column(name = "answered", nullable = false, updatable = false)
    private Boolean answered;

    @Column(name = "view_count", nullable = false, updatable = false)
    private Integer viewCount;

    @Column(name = "answer_count", nullable = false, updatable = false)
    private Integer answerCount;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;
}
