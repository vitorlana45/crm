package com.lanDev.crm.application.service.user;

import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserRoleJpa;
import com.lanDev.crm.application.service.auth.UserContextService;
import com.lanDev.crm.domain.domainExceptions.BusinessException;
import com.lanDev.crm.domain.domainExceptions.EmailAlreadyExistsException;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.model.user.UserRole;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserCommand;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserUseCase;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContextService userContextService;

    @Override
    public User register(RegisterUserCommand cmd) {

        UserRoleJpa currentRole = userContextService.getCurrentUserRole();

        UserRoleJpa desiredRole = cmd.role() == null
                ? UserRoleJpa.USER
                : UserRoleJpa.valueOf(cmd.role().name());

        if (currentRole == UserRoleJpa.ADMIN && desiredRole == UserRoleJpa.MANAGER) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "User with role MANAGER cannot be created by ADMIN");
        }

        if (userRepository.existsByEmail(cmd.email())) {
            throw new EmailAlreadyExistsException(cmd.email());
        }

        String hashed = passwordEncoder.encode(cmd.password());
        User toSave = User.newUser(
                cmd.name(),
                cmd.email(),
                hashed,
                UserRole.valueOf(desiredRole.name()),
                cmd.active()
        );
        return userRepository.save(toSave);
    }
}
