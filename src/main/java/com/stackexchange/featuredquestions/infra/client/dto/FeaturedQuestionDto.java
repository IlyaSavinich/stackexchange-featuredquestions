package com.stackexchange.featuredquestions.infra.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stackexchange.featuredquestions.infra.deserializer.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturedQuestionDto {
    private Integer id;
    private List<String> tags;
    @JsonProperty("is_answered")
    private Boolean answered;
    @JsonProperty("view_count")
    private Integer viewCount;
    @JsonProperty("answer_count")
    private Integer answerCount;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("owner")
    public void unpackUserId(Map<String, String> map) {
        userId = Integer.parseInt(map.get("user_id"));
    }
}
