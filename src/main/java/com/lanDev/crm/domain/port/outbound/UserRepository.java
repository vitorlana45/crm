package com.lanDev.crm.domain.port.outbound;

import com.lanDev.crm.domain.model.user.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findByEmail(String email);
}
