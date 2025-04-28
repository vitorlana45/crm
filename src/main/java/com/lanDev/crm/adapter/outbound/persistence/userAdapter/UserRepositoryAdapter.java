package com.lanDev.crm.adapter.outbound.persistence.userAdapter;

import com.lanDev.crm.adapter.outbound.persistence.SpringDataUserRepository;
import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserJpaEntity;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository jpaRepo;
    private final UserPersistenceMapper mapper;

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepo.existsByEmail(email);
    }

    @Override
    public User save(User userDomain) {

        UserJpaEntity jpaEntity = mapper.toEntity(userDomain);
        jpaEntity = jpaRepo.save(jpaEntity);
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public Optional<User> getByUuid(UUID id) {
        Optional<UserJpaEntity> jpaEntity = jpaRepo.getUserByUuid(id);
        if (jpaEntity.isPresent()) return jpaEntity.map(mapper::toDomain);
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<UserJpaEntity> jpaEntity = jpaRepo.findByEmail(email);
        return Optional.ofNullable(jpaEntity.map(mapper::toDomain).orElse(null));
    }

    @Override
    public void deleteById(UUID uuid) {
        jpaRepo.deleteById(uuid);
    }

   @Override
   public void activateUser(UUID uuid) {
       Optional<UserJpaEntity> entityOpt = jpaRepo.getUserByUuid(uuid);
       if (entityOpt.isPresent()) {
           UserJpaEntity entity = entityOpt.get();
           entity.setActive(true);
           jpaRepo.save(entity);
       }
   }

    @Override
    public void deactivateUser(UUID uuid) {
        Optional<UserJpaEntity> entityOpt = jpaRepo.getUserByUuid(uuid);
        if (entityOpt.isPresent()) {
            UserJpaEntity entity = entityOpt.get();
            entity.setActive(false);
            jpaRepo.save(entity);
        }
    }

    public List<User> findAllByOrganizationId(UUID organizationId) {
        return jpaRepo.findAllByOrganizationUuid(organizationId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}

