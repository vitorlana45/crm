package com.lanDev.crm.adapter.inbound.web.validation;

import com.lanDev.crm.adapter.inbound.web.dtos.user.CreateUserRequest;
import com.lanDev.crm.adapter.inbound.web.exceptions.setup.FieldMessage;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, CreateUserRequest> {

    private final UserRepository repository;

    @Override
    public boolean isValid(CreateUserRequest dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        repository.getUserByEmail(dto.email()).ifPresent(user -> list.add(new FieldMessage("email", "Email já existe")));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                  .addPropertyNode(e.getFieldName())
                  .addConstraintViolation();
        }
        return list.isEmpty();
    }
}