package com.lanDev.crm.domain.port.outbound;

import com.lanDev.crm.domain.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmail(String email);
    User save(User user);
    Optional<User> getByUuid(UUID id);
    Optional<User> getUserByEmail(String email);
    void deleteById(UUID id);
    void activateUser(UUID id);
    void deactivateUser(UUID id);

}
