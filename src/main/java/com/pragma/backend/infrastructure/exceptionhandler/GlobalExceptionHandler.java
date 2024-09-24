package com.pragma.backend.infrastructure.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pragma.backend.application.response.ErrorResponse;
import com.pragma.backend.application.response.Errores;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST) // Devuelve un 400 Bad Request
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<Errores> listaErrores = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			listaErrores.add(new Errores(fieldName, errorMessage));
		});
		
		ErrorResponse errorResponse = new ErrorResponse("Validacion Fallida", listaErrores);
		return ResponseEntity.badRequest().body(errorResponse);
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) // Devuelve un 400 Bad Request
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public String handleValidationExceptions2(HttpMessageNotReadableException ex) {
		return "No hay cuerpo de solicitud";
    }

}