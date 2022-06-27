package com.stackexchange.featuredquestions.infra.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stackexchange.featuredquestions.infra.deserializer.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class UserResponseDto {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("creation_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;
    @JsonProperty("display_name")
    private String displayName;
}
