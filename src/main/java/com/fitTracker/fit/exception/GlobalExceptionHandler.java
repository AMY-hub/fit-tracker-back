package com.fitTracker.fit.exception;

import com.fitTracker.fit.dto.responseDto.error.ErrorDto;
import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.*;

@AllArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AbstractRuntimeException.class)
    public ResponseEntity<Object> callRuntimeException(AbstractRuntimeException ex) {
        log.error("ERROR: " + ex.getMessage());
        log.debug(Arrays.toString(ex.getStackTrace()));

        return getResponseEntityWithErrorDto(ex.getHttpStatus(), ex.getType(), ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return getResponseEntityWithErrorDto(HttpStatus.BAD_REQUEST, ErrorType.SYSTEM, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("ERROR: " + ex.getMessage());
        log.debug(Arrays.toString(ex.getStackTrace()));

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> Collections.singletonMap(e.getField(), e.getDefaultMessage()))
                .toList();

        return getResponseEntityWithErrorDto(HttpStatus.BAD_REQUEST, ErrorType.VALIDATION, ex.getAllErrors().get(0).getDefaultMessage(), errors);
    }

    private ResponseEntity<Object> getResponseEntityWithErrorDto(HttpStatus statusCode, ErrorType errorType, String message) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(statusCode.value())
                .type(errorType)
                .date(OffsetDateTime.now())
                .message(message)
                .build();
        return new ResponseEntity<Object>(errorDto, statusCode);
    }

    private ResponseEntity<Object> getResponseEntityWithErrorDto(HttpStatus statusCode, ErrorType errorType, String message, List<Map<String, String>> errors) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(statusCode.value())
                .type(errorType)
                .date(OffsetDateTime.now())
                .message(message)
                .errors(errors)
                .build();
        return new ResponseEntity<Object>(errorDto, statusCode);
    }
}
