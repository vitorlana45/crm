// src/main/java/com/lanDev/crm/domain/port/inbound/organization/OrganizationUseCase.java
package com.lanDev.crm.domain.port.inbound.organization;

import com.lanDev.crm.domain.model.organization.Organization;
import java.util.List;
import java.util.UUID;

public interface OrganizationUseCase {
    Organization create(CreateOrganizationCommand command);
    Organization update(UpdateOrganizationCommand command);
    Organization getById(UUID id);
    List<Organization> getAll();
    void deleteOrganization(UUID id);
}