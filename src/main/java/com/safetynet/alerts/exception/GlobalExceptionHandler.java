package com.safetynet.alerts.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.PushBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // paramétrable pour un seul controller si besoin
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    // exception sur validation des données en entrée: renvoie la liste des erreurs pour chaque champs erroné
    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleValidArgException(MethodArgumentNotValidException exception, WebRequest request)  {
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

    //NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response =  new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        logger.error("request with NotFoundException : received = {}, response ={}", received, response);

        return response;
    }

    @ExceptionHandler(NoContainException.class)
    public ResponseEntity<Object> handleNoContainException(HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response = new ResponseEntity<>("{}",HttpStatus.OK);
        logger.error("request with NoContainException : received = {}, response ={}", received, response);

        return response;
        // TODO voir comment retourner chaine vide en cas de 204 --> peut-être pas possible  (fonctionne si 200)
        //return new ResponseEntity<>("{}",HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response =  new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        logger.error("request with AlreadyExistsException : received = {}, response ={}", received, response);
        return response;
    }

    @ExceptionHandler(GivenDataMismatchException.class)
    public ResponseEntity<Object> handleValidException(GivenDataMismatchException exception, HttpServletRequest request)  {
        String received = ( "Method: " + request.getMethod() + " - URI: " + request.getRequestURI() + " - queryString: "
                            + request.getQueryString() );
        ResponseEntity<Object> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        logger.error("request with GivenDataMismatchException : received = {}, response ={}", received, response);
        return response;
    }

   //TODO: voir comment récupérer une exception par défaut et logguer la requete + réponse : ex 404 sur page inconnue, 400
//    @ExceptionHandler(Exception.class)
    //

}