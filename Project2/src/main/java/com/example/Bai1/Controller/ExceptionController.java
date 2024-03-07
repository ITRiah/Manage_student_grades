package com.example.Bai1.Controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Bai1.dto.ResponseDTO;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
	
	@ExceptionHandler({NoResultException.class})
	public ResponseDTO<String> notFound(NoResultException e){
		log.info("info", e);
		return ResponseDTO.<String>builder()
				.status(404)
				.msg("No data")
				.build();
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseDTO<String> notFound(MethodArgumentNotValidException e){		
		
		List<ObjectError> errors = e.getAllErrors();
		String msg = "";
		for (ObjectError err : errors) {
			FieldError fieldError =  (FieldError) err;
			msg += fieldError.getField() + ":" + err.getDefaultMessage();
		}
		
		return ResponseDTO.<String>	builder()
				.status(404)
				.msg(msg)
				.build();
	}
	
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class})
	@ResponseStatus(code = HttpStatus.CONFLICT )
	public ResponseDTO<String> duplicate(SQLIntegrityConstraintViolationException e){
		log.info("INFO" , e);
		return ResponseDTO.<String>builder()
				.status(409)
				.msg("duplicate data")
				.build();
	}
	
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseDTO<String> duplicate(DataIntegrityViolationException e){
		log.info("INFO" , e);
		 String errorMessage = "Lỗi ràng buộc dữ liệu";		   
		return ResponseDTO.<String>builder()
				.status(409)
				.msg(errorMessage)
				.build();
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	@ResponseStatus(code = HttpStatus.FORBIDDEN)	
	public ResponseDTO<String> accessDenied(AccessDeniedException e){
		log.info("INFO", e);
		return ResponseDTO.<String>builder()
				.status(403)
				.msg("Access deny")
				.build();
	}
	
	@ExceptionHandler({BadCredentialsException.class})
	@ResponseStatus(code = HttpStatus.FORBIDDEN)	
	public ResponseDTO<String> unathorized(BadCredentialsException e){
		log.info("INFO", e);
		return ResponseDTO.<String>builder()
				.status(401)
				.msg(e.getMessage())
				.build();
	}
}
