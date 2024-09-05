package com.musinsa.product.advice;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.musinsa.product.model.Response;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalAdvice {
    @ExceptionHandler
    public Response errorHandler(WebExchangeBindException e) {
        BindingResult bindingResult =  e.getBindingResult();
        String message = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));

        return Response.builder()
                .code(400)
                .message(message)
                .build();
    }
    
    @ExceptionHandler
    public Response errorHandler(MissingServletRequestParameterException e) {
        return Response.builder()
                .code(400)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public Response errorHandler(ConstraintViolationException e) {
        return Response.builder()
                .code(400)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public Response errorHandler(Exception e) {
        e.printStackTrace();
        return Response.builder()
                .code(500)
                .message(e.getMessage())
                .build();
    }
}

