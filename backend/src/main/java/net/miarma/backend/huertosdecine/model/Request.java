package net.miarma.backend.huertosdecine.model;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.*;
import net.miarma.backlib.util.UuidUtil;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cine_requests")
public class Request {

    @Id
    @Column(name = "request_id", columnDefinition = "BINARY(16)")
    private byte[] requestIdBin;

    @Transient
    private UUID requestId;

    @Column(name = "display_name", nullable = false, length = 128)
    private String displayName;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private Byte status;

    @CreationTimestamp
    private Instant createdAt;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (requestId != null) requestIdBin = UuidUtil.uuidToBin(requestId);
    }

    @PostLoad
    private void postLoad() {
        if (requestIdBin != null) requestId = UuidUtil.binToUUID(requestIdBin);
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

