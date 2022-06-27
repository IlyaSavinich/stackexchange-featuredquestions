package com.stackexchange.featuredquestions.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private Integer code;
    private String message;
}
