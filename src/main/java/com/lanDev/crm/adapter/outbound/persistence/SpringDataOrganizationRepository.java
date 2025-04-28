package com.lanDev.crm.adapter.outbound.persistence;

import com.lanDev.crm.adapter.outbound.persistence.entity.organization.OrganizationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataOrganizationRepository extends JpaRepository<OrganizationJpaEntity, UUID> {
    boolean existsByDomain(String domain);
    boolean existsByName(String name);
    Optional<OrganizationJpaEntity> findByUuid(UUID uuid);
    void deleteById(UUID uuid);
}
