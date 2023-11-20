package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // exception sur validation des données en entrée: renvoie la liste des erreurs pour chaque champs erroné
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException exception)  {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    //NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleValidException(NotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoContainException.class)
    public ResponseEntity<Object> handleValidException()  {
        return new ResponseEntity<>("{}",HttpStatus.OK);
        // TODO voir comment retourner chaine vide en cas de 204 --> peut-être pas possible  (fonctionne si 200)
        //return new ResponseEntity<>("{}",HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleValidException(AlreadyExistsException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GivenDataMismatchException.class)
    public ResponseEntity<Object> handleValidException(GivenDataMismatchException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}