package com.safetynet.alerts.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // paramétrable pour un seul controller si besoin
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleValidArgException(HttpMessageNotReadableException exception, HttpServletRequest request)  {
//        exception.printStackTrace();
        ResponseEntity<Object> response = new ResponseEntity<>("Invalid JSON",HttpStatus.BAD_REQUEST);

        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                + request.getQueryString() );
        logger.error("request with invalid JSON : received = {}, response ={}", received, response);

        return response;
    }
    // exception sur validation des données en entrée: renvoie la liste des erreurs pour chaque champs erroné
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidArgException(MethodArgumentNotValidException exception, HttpServletRequest request)  {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ResponseEntity<Object> response = new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        logger.error("request with invalid field(s) : received = {}, response ={}", received, response);

        return response;
    }

    // exception sur validation des requestParameters en entrée
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleValidParamException(MissingServletRequestParameterException exception,
                                                            HttpServletRequest request)  {
        String message = "missing parameter : " + exception.getParameterName();
        ResponseEntity<Object> response = new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);

        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        logger.error("request with missing parameter : received = {}, response ={}", received, response);

        return response;
    }

    // exception sur validation des contraintes (sur pathvariable par exemple)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleOtherValidException(ConstraintViolationException exception, HttpServletRequest request)  {
        Map<Path, String> errors = new HashMap<>();
        exception.getConstraintViolations().forEach(error -> {
            Path propertyPath = error.getPropertyPath();
            String message = error.getMessage();
            errors.put(propertyPath, message);
        });
        ResponseEntity<Object> response = new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                + request.getQueryString() );
        logger.error("request with invalid variables : received = {}, response ={}", received, response);

        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response =  new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        logger.error("request with NotFoundException : received = {}, response ={}", received, response);

        return response;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response =  new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        logger.error("request with AlreadyExistsException : received = {}, response ={}", received, response);
        return response;
    }

    @ExceptionHandler(UnreachableDatabaseException.class)
    public ResponseEntity<Object> handleValidArgException(UnreachableDatabaseException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                + request.getQueryString() );
        ResponseEntity<Object> response =  new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error("request with DataBaseException : received = {}, response ={}", received, response);

        return response;
    }

    // pour trapper les autres erreurs non précédemment trappées
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                + request.getQueryString() );
        //todo : voir comment laisser code http par défaut pour tout ce qui est de type 400
        ResponseEntity<Object> response =  new ResponseEntity<>("unexpected internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error("request with other Exception : received = {}, exception = {}", received, exception);
        exception.printStackTrace();
        return response;
    }

}