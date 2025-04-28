package com.lanDev.crm.adapter.outbound.persistence.organizationAdapter;

import com.lanDev.crm.adapter.outbound.persistence.entity.organization.OrganizationJpaEntity;
import com.lanDev.crm.domain.model.organization.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface OrganizationPersistenceMapper {

     @Mapping(target = "uuid", ignore = true)
     @Mapping(target = "active", source = "active")
     OrganizationJpaEntity toEntity(Organization organization);

     @Mapping(target = "active", source = "active")
     Organization toDomain(OrganizationJpaEntity entity);

}
