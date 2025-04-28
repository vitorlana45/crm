package com.lanDev.crm.adapter.outbound.persistence.organizationAdapter;

import com.lanDev.crm.adapter.outbound.persistence.SpringDataOrganizationRepository;
import com.lanDev.crm.adapter.outbound.persistence.entity.organization.OrganizationJpaEntity;
import com.lanDev.crm.domain.model.organization.Organization;
import com.lanDev.crm.domain.port.outbound.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryAdapter implements OrganizationRepository {

    private final SpringDataOrganizationRepository OrgJpaRepo;
    private final OrganizationPersistenceMapper mapper;


    @Override
    public Organization save(Organization organization) {
        OrganizationJpaEntity entity = mapper.toEntity(organization);
        entity = OrgJpaRepo.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Organization> getById(UUID id) {
        Optional<OrganizationJpaEntity> entityOpt = OrgJpaRepo.findByUuid(id);
        return entityOpt.map(mapper::toDomain);
    }

    @Override
    public List<Organization> findAll() {
        return OrgJpaRepo.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        OrgJpaRepo.deleteById(id);
    }

    @Override
    public boolean existsByDomain(String domain) {
        return OrgJpaRepo.existsByDomain(domain);
    }
}
