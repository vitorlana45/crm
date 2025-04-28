package com.lanDev.crm.adapter.outbound.persistence.entity.organization;

import com.lanDev.crm.adapter.outbound.persistence.entity.VersionedAuditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationJpaEntity extends VersionedAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String domain;

    @Column(name = "is_active")
    private boolean active;

}