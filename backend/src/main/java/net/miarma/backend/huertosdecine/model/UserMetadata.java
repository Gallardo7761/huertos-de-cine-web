package net.miarma.backend.huertosdecine.model;

import jakarta.persistence.*;
import net.miarma.backlib.util.UuidUtil;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cine_user_metadata")
public class UserMetadata {
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private byte[] userIdBin;

    @Transient
    private UUID userId;

    @Column(name = "role", nullable = false)
    private Byte role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deactivated_at")
    private Instant deactivatedAt;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (userId != null) {
            userIdBin = UuidUtil.uuidToBin(userId);
        }
    }

    @PostLoad
    private void postLoad() {
        if (userIdBin != null) {
            userId = UuidUtil.binToUUID(userIdBin);
        }
    }

    public byte[] getUserIdBin() {
        return userIdBin;
    }

    public void setUserIdBin(byte[] userIdBin) {
        this.userIdBin = userIdBin;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getDeactivatedAt() {
        return deactivatedAt;
    }

    public void setDeactivatedAt(Instant deactivatedAt) {
        this.deactivatedAt = deactivatedAt;
    }
}
