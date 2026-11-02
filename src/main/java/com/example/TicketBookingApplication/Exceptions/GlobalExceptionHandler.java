package com.example.TicketBookingApplication.Exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    private static  final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler (BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex){

        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(400);
        errorMessage.setMessage(ex.getMessage());

        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler (AuthorizationException.class)
    public ResponseEntity<ErrorMessage> handleAuthorizationException(AuthorizationException ex){

        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(403);
        errorMessage.setMessage(ex.getMessage());

        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler (RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex){

        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(500);
        errorMessage.setMessage(ex.getMessage());

        log.error("Internal Error has been occurred");;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }


    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex){

        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(404);
        errorMessage.setMessage(ex.getMessage());

        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler (SeatUnavailableException.class)
    public ResponseEntity<ErrorMessage> handleSeatUnavailableException(SeatUnavailableException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(409);
        errorMessage.setMessage(ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler (OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorMessage> handleOptimisticException(OptimisticLockingFailureException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setStatus(409);
        errorMessage.setMessage(ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }


    
    
}
