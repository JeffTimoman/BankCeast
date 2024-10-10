package com.myapp.bankceast.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiBadRequestException.class, BadRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiBadRequestException e, HttpServletRequest request){
        //Payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final String requestURI = request.getRequestURI();
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(badRequest)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .route(requestURI)
                .build();
        //Return response entity
        return new ResponseEntity<>(apiException,badRequest);
    }

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e, HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z")),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, notFound);
    }
}
