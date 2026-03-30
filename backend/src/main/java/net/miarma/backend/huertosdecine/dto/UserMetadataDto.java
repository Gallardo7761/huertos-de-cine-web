package net.miarma.backend.huertosdecine.dto;

import java.time.Instant;
import java.util.UUID;

public class UserMetadataDto {
    public static class Request {
        private UUID userId;
        private Byte role;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public Byte getRole() { return role; }
        public void setRole(Byte role) { this.role = role; }
    }

    public static class Response {
        private UUID userId;
        private Byte role;
        private Instant createdAt;
        private Instant deactivatedAt;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public Byte getRole() { return role; }
        public void setRole(Byte role) { this.role = role; }
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

        public Instant getDeactivatedAt() {
            return deactivatedAt;
        }

        public void setDeactivatedAt(Instant deactivatedAt) {
            this.deactivatedAt = deactivatedAt;
        }
    }
}
