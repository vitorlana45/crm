package com.lanDev.crm.domain.port.inbound.user;

import com.lanDev.crm.domain.model.user.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
