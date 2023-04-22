package com.unibuc.ro.exceptionHandling;

import com.unibuc.ro.exceptions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralExceptionHandler {
    private Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

//    @ExceptionHandler({HolidayAlreadyCancelledException.class})
//    public ResponseEntity<String> handleCancelledException(HolidayAlreadyCancelledException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
    @ExceptionHandler({HolidayAlreadyCancelledException.class,
            HolidayCannotBeCancelledException.class,
            AccommodationNotMatchingDestException.class,
            EntityNotFoundException.class, ParseException.class})
    public String handleCancelledException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/holidays";
    }

    @ExceptionHandler({InvalidSessionException.class})
    public String handleInvalidSession(InvalidSessionException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/invalidSession";

    }
    @ExceptionHandler({ClientNotRegisteredException.class})
    public String handleNotRegisteredClient(ClientNotRegisteredException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/signup";

    }
    @ExceptionHandler({DestinationAlreadyExistsException.class})
    public String handleDestinationAlreadyExists(DestinationAlreadyExistsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        LOGGER.info(e.getMessage());
        return "redirect:/destinations/list";

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
