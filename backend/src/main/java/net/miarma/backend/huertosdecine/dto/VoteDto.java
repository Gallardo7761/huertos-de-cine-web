package net.miarma.backend.huertosdecine.dto;

import java.time.Instant;
import java.util.UUID;

public class VoteDto {
    public static class Request {
        private UUID userId;
        private UUID movieId;
        private Byte vote;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public UUID getMovieId() { return movieId; }
        public void setMovieId(UUID movieId) { this.movieId = movieId; }
        public Byte getVote() { return vote; }
        public void setVote(Byte vote) { this.vote = vote; }
    }

    public static class Response {
        private UUID userId;
        private UUID movieId;
        private Byte vote;
        private Instant createdAt;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public UUID getMovieId() { return movieId; }
        public void setMovieId(UUID movieId) { this.movieId = movieId; }
        public Byte getVote() { return vote; }
        public void setVote(Byte vote) { this.vote = vote; }
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    }
}
