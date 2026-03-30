package net.miarma.backend.huertosdecine.dto;

import java.time.Instant;
import java.util.UUID;

public class RequestDto {
    public static class Request {
        private String displayName;
        private String username;
        private String password;
        private Byte status;

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Byte getStatus() { return status; }
        public void setStatus(Byte status) { this.status = status; }
    }

    public static class Response {
        private UUID requestId;
        private String displayName;
        private String username;
        private Byte status;
        private Instant createdAt;

        public UUID getRequestId() { return requestId; }
        public void setRequestId(UUID requestId) { this.requestId = requestId; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public Byte getStatus() { return status; }
        public void setStatus(Byte status) { this.status = status; }
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    }
}
