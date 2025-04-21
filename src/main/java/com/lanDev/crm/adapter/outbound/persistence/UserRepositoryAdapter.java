package com.lanDev.crm.adapter.outbound.persistence;

import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserJpaEntity;
import com.lanDev.crm.adapter.outbound.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        UserJpaEntity saved = jpaRepo.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email)
                .map(mapper::toDomain);
    }
}

