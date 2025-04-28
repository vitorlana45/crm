package com.lanDev.crm.application.service.organization;

import com.lanDev.crm.domain.model.organization.Organization;
import com.lanDev.crm.domain.port.inbound.organization.*;
import com.lanDev.crm.domain.port.outbound.OrganizationRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService implements OrganizationUseCase {
    private final OrganizationRepository organizationRepository;

    @Override
    public Organization create(CreateOrganizationCommand command) {
        return null;
    }

    @Override
    public Organization update(UpdateOrganizationCommand command) {
        return null;
    }

    @Override
    public Organization getById(UUID id) {
        return null;
    }

    @Override
    public List<Organization> getAll() {
        return List.of();
    }

    @Override
    public void deleteOrganization(UUID id) {

    }

    // Implementações dos métodos...
}