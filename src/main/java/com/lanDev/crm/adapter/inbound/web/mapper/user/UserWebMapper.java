package com.lanDev.crm.adapter.inbound.web.mapper.user;

import com.lanDev.crm.adapter.inbound.web.controllers.user.UpdateUserRequest;
import com.lanDev.crm.adapter.inbound.web.dtos.user.CreateUserRequest;
import com.lanDev.crm.adapter.inbound.web.dtos.user.UserResponse;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserCommand;
import com.lanDev.crm.domain.port.inbound.user.UpdateUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "active", source = "active")
    RegisterUserCommand toCommand(CreateUserRequest req);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "active", source = "active")
    UserResponse toResponse(User user);


    UpdateUserCommand toUpdateCommand(UUID uuid, UpdateUserRequest req);


    UpdateResponse toUpdateResponse(User user);



}
