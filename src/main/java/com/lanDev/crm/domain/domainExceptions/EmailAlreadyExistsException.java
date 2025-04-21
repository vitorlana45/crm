package com.lanDev.crm.domain.domainExceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(String email) {
        super(
            HttpStatus.CONFLICT,            // ou BAD_REQUEST, se preferir
            String.format("Email '%s' já cadastrado", email)
        );
    }
}
