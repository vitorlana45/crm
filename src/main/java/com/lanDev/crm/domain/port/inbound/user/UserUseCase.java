package com.lanDev.crm.domain.port.inbound.user;

import com.lanDev.crm.domain.model.user.User;

import java.util.UUID;

public interface UserUseCase {
    User register(RegisterUserCommand command);
    User updateUser(UpdateUserCommand command, UUID uuid);
    User getUserById(UUID uuid);
    User getUserByEmail(String email);
    void deleteUser(UUID uuid);
    void activateUser(UUID uuid);
    void deactivateUser(UUID uuid);
}
