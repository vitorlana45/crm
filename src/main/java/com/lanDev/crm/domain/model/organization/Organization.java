package com.lanDev.crm.domain.model.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public class Organization {
    private final UUID uuid;
    private String name;
    private String domain;
    private String plan;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    public Organization(UUID uuid, String name, String domain, String plan, LocalDateTime createdAt, LocalDateTime updatedAt, boolean active) {
        this.uuid = uuid;
        this.name = name;
        this.domain = domain;
        this.plan = plan;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public static Organization newOrganization(String name, String domain, String plan) {
        LocalDateTime now = LocalDateTime.now();
        return new Organization(UUID.randomUUID(), name, domain, plan, now, now, true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUuid() {
        return uuid;
    }
}