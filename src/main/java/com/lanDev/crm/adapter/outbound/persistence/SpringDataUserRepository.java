package com.lanDev.crm.adapter.outbound.persistence;

import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    boolean existsByEmail(String email);
    Optional<UserJpaEntity> findByEmail(String email);
}
