package ru.gw3nax.scrapper.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gw3nax.scrapper.exception.dto.ErrorResponse;
import ru.gw3nax.scrapper.exception.exceptions.UserAlreadyRegisteredException;
import ru.gw3nax.scrapper.exception.exceptions.UserNotRegisteredException;

import java.util.Arrays;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ErrorResponse handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .stackTrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList())
                .build();
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    public ErrorResponse handleUserNotRegisteredException(UserNotRegisteredException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .stackTrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList())
                .build();
    }
}
