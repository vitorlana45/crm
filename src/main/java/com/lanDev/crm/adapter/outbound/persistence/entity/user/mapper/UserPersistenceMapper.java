package com.lanDev.crm.adapter.outbound.persistence.entity.user.mapper;

import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserJpaEntity;
import com.lanDev.crm.domain.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserPersistenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", source = "active")
    UserJpaEntity toEntity(User user);

    @Mapping(target = "active", source = "active")
    User toDomain(UserJpaEntity entity);
}
