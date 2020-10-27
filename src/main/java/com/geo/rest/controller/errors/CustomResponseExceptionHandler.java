package com.geo.rest.controller.errors;

import com.geo.rest.service.exception.ProvidedDeviceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(exception, apiError, headers, apiError.getHttpStatus(), request);
    }

    @ExceptionHandler(ProvidedDeviceNotFoundException.class)
    public ResponseEntity<ApiError> handleProvidedDeviceNotFoundException(ProvidedDeviceNotFoundException exception, WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, Arrays.asList(exception.getMessage()));
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }
}
