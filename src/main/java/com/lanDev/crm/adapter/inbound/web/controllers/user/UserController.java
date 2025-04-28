package com.lanDev.crm.adapter.inbound.web.controllers.user;

import com.lanDev.crm.adapter.inbound.web.dtos.user.CreateUserRequest;
import com.lanDev.crm.adapter.inbound.web.dtos.user.UserResponse;
import com.lanDev.crm.adapter.inbound.web.mapper.user.UserWebMapper;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserCommand;
import com.lanDev.crm.domain.port.inbound.user.UpdateUserCommand;
import com.lanDev.crm.domain.port.inbound.user.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserWebMapper userWebMapper;


    @PostMapping()
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN','ORG_OWNER')")
    ResponseEntity<UserResponse> createUser(@RequestBody final @Valid CreateUserRequest req) {
        RegisterUserCommand cmd = userWebMapper.toCommand(req);
        User user = userUseCase.register(cmd);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        UserResponse resp = userWebMapper.toResponse(user);

        return ResponseEntity.created(location).body(resp);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN','ORG_OWNER','MANAGER')")
    ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        User user = userUseCase.getUserById(id);
        UserResponse resp = userWebMapper.toResponse(user);
        return ResponseEntity.ok(resp);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(''SUPER_ADMIN','ORG_ADMIN','ORG_OWNER')")
    ResponseEntity<UserResponse> updateUser(@PathVariable UUID uuid, @RequestBody final @Valid com.lanDev.crm.adapter.inbound.web.controllers.user.UpdateUserRequest req) {
        
        UpdateUserCommand cmd = userWebMapper.toUpdateCommand(uuid, req);
        User user = userUseCase.updateUser(cmd, uuid);
        UserResponse resp = userWebMapper.toResponse(user);
        
        return ResponseEntity.ok(resp);
    }
    
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN','ORG_OWNER')")
    ResponseEntity<Void> activateUser(@PathVariable UUID id) {
        userUseCase.activateUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ORG_ADMIN','ORG_OWNER')")
    ResponseEntity<Void> deactivateUser(@PathVariable UUID id) {
        userUseCase.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN','ORG_OWNER')")
    ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
