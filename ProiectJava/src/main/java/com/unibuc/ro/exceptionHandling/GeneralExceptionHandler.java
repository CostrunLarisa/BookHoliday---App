package com.unibuc.ro.exceptionHandling;

import com.unibuc.ro.exceptions.ClientNotRegisteredException;
import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.exceptions.HolidayAlreadyCancelledException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({HolidayAlreadyCancelledException.class})
    public ResponseEntity<String> handleCancelledException(HolidayAlreadyCancelledException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ClientNotRegisteredException.class})
    public ResponseEntity<String> handleNotRegisteredClient(ClientNotRegisteredException e) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/client");
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
            response.close();
            return ResponseEntity.status(HttpStatus.OK).body("Before choosing a destination please register.");
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String,String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
