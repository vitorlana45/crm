package com.lanDev.crm.adapter.inbound.web.controllers.auth;

import com.lanDev.crm.adapter.inbound.web.dtos.auth.LoginRequest;
import com.lanDev.crm.adapter.inbound.web.dtos.auth.LoginResponse;
import com.lanDev.crm.domain.port.inbound.auth.AuthenticationServicePort;
import com.lanDev.crm.domain.port.inbound.auth.AuthResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

     private final AuthenticationServicePort authenticationServicePort;

    @PostMapping()
    ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {

        AuthResult result = authenticationServicePort.authenticate(loginRequest.email(), loginRequest.password());

        return ResponseEntity.ok().body(new LoginResponse(result.name(), result.token(), result.expirationDate()));
    }
}
