package com.lendico.coding.codingtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author Anil Kurmi
 */
@ControllerAdvice
@RestController
public class ControlAdviceHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        PaymentException paymentException = new PaymentException(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(paymentException, HttpStatus.BAD_REQUEST);
    }
    
}
