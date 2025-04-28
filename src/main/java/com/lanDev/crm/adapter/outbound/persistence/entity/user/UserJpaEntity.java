package com.lanDev.crm.adapter.outbound.persistence.entity.user;

import com.lanDev.crm.adapter.outbound.persistence.entity.VersionedAuditable;
import com.lanDev.crm.adapter.outbound.persistence.entity.organization.OrganizationJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity extends VersionedAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleJpa role;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationJpaEntity organization;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserJpaEntity that = (UserJpaEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

}
