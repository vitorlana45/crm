package com.lanDev.crm.adapter.inbound.web.controllers.user;

import com.lanDev.crm.adapter.inbound.web.dtos.user.CreateUserRequest;
import com.lanDev.crm.adapter.inbound.web.dtos.user.UserResponse;
import com.lanDev.crm.adapter.inbound.web.mapper.user.UserWebMapper;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserCommand;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUserUseCase registerUser;
    private final UserWebMapper userWebMapper;


    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    ResponseEntity<UserResponse> createUser(@RequestBody final @Valid CreateUserRequest req) {

        RegisterUserCommand cmd = userWebMapper.toCommand(req);
        User user = registerUser.register(cmd);

        URI localtion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        UserResponse resp = userWebMapper.toResponse(user);

        return ResponseEntity.created(localtion).body(resp);

    }
}
