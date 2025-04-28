package com.lanDev.crm.domain.model.user;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private final UUID organizationId;

    public User(UUID uuid, String name, String email, String password, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt, boolean active, UUID organizationId) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.organizationId = organizationId;
    }

    public static User newUser(String name, String email, String password, UserRole role, boolean active, UUID organizationId) {
        LocalDateTime now = LocalDateTime.now();
        return new User(UUID.randomUUID(), name, email, password, role, now, now, active, organizationId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    public void deactivate() {
        if (!active) return;
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return uuid;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

}
