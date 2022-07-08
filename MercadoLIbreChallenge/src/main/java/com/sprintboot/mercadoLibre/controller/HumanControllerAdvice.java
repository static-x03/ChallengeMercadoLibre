package com.sprintboot.mercadoLibre.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sprintboot.mercadoLibre.exception.RepositoryExeption;

@ControllerAdvice
public class HumanControllerAdvice extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(RepositoryExeption.class)
	public ResponseEntity<?> exceptionHandler(){
		Map<String, Object> body = new HashMap<>();
		 
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "El dna está repetido");

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
