package com.lanDev.crm.adapter.inbound.web.dtos.user;

import com.lanDev.crm.adapter.inbound.web.validation.UserInsertValid;
import com.lanDev.crm.domain.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


@UserInsertValid
public record CreateUserRequest(

    @NotBlank(message = "Name is required")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @Email(message = "Email is invalid")
    @Length(min = 3, max = 320, message = "Email deve ter entre 3 e 320 caracteres")
     String email,

    @NotBlank(message = "Password is required")
    @Length(min = 6, max = 30, message = "Senha deve ter entre 6 e 50 caracteres")
    String password,

    @NotNull(message = "Role is required")
    UserRole role,

    @NotNull(message = "isActive is required")
    boolean active
    ) { }

