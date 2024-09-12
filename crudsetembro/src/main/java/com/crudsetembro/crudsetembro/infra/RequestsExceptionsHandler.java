package com.crudsetembro.crudsetembro.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //indica que a classe lida com excessões;
public class RequestsExceptionsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity treat404(){
        var response = new ExceptionDTO("Dado não encontrado.");
        return ResponseEntity.badRequest().body(response);
    }

}
