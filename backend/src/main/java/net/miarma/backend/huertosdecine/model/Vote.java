package net.miarma.backend.huertosdecine.model;

import jakarta.persistence.*;
import net.miarma.backlib.util.UuidUtil;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cine_votes")
@IdClass(VoteId.class)
public class Vote {

    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private byte[] userIdBin;

    @Id
    @Column(name = "movie_id", columnDefinition = "BINARY(16)")
    private byte[] movieIdBin;

    @Transient
    private UUID userId;

    @Transient
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    private Byte vote;

    @CreationTimestamp
    private Instant createdAt;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (userId != null) userIdBin = UuidUtil.uuidToBin(userId);
        if (movieId != null) movieIdBin = UuidUtil.uuidToBin(movieId);
    }

    @PostLoad
    private void postLoad() {
        if (userIdBin != null) userId = UuidUtil.binToUUID(userIdBin);
        if (movieIdBin != null) movieId = UuidUtil.binToUUID(movieIdBin);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public Byte getVote() {
        return vote;
    }

    public void setVote(Byte vote) {
        this.vote = vote;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
