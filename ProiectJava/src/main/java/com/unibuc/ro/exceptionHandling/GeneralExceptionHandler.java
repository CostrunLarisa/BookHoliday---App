package com.unibuc.ro.exceptionHandling;

import com.unibuc.ro.exceptions.ClientNotRegisteredException;
import com.unibuc.ro.exceptions.EntityNotFoundException;
import com.unibuc.ro.exceptions.HolidayAlreadyCancelled;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({HolidayAlreadyCancelled.class})
    public ResponseEntity<String> handleCancelledException(HolidayAlreadyCancelled e) {
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
}
