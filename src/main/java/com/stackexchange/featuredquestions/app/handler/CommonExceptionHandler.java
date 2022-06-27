package com.stackexchange.featuredquestions.app.handler;

import com.stackexchange.featuredquestions.app.response.ErrorResponseDto;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), entityNotFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponseDto handleTypeMismatchException(TypeMismatchException exception) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }


}
