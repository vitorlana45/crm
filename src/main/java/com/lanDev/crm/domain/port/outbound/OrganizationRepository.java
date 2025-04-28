package com.lanDev.crm.domain.port.outbound;

import com.lanDev.crm.domain.model.organization.Organization;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {
    Organization save(Organization organization);
    Optional<Organization> getById(UUID id);
    List<Organization> findAll();
    void delete(UUID id);
    boolean existsByDomain(String domain);
}