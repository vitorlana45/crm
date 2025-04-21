package com.lanDev.crm.adapter.inbound.web.exceptions;


import com.lanDev.crm.adapter.inbound.web.exceptions.setup.StandardError;
import com.lanDev.crm.adapter.inbound.web.exceptions.setup.ValidationError;
import com.lanDev.crm.domain.domainExceptions.BusinessException;
import com.lanDev.crm.domain.domainExceptions.EmailAlreadyExistsException;
import com.lanDev.crm.domain.domainExceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> onNotFound(
            EntityNotFoundException ex, HttpServletRequest req
    ) {
        StandardError err = new StandardError(
                Instant.now(),
                ex.getStatusCode().value(),
                "Resource not found",
                ex.getMessage(),
                req.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<StandardError> onDuplicate(
            EmailAlreadyExistsException ex, HttpServletRequest req
    ) {
        StandardError err = new StandardError(
                Instant.now(),
                ex.getStatusCode().value(),
                "Conflict",
                ex.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> onValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req
    ) {
        ValidationError error = new ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setError("Validation error");
        error.setMessage("Validation error in fields");
        error.setPath(req.getRequestURI());


        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> onBusiness(BusinessException ex, HttpServletRequest req) {
        StandardError err = new StandardError(
                Instant.now(),
                ex.getStatusCode().value(),
                "Business error",
                ex.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }
}
