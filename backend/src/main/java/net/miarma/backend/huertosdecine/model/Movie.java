package net.miarma.backend.huertosdecine.model;

import jakarta.persistence.*;
import net.miarma.backlib.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cine_movies")
public class Movie {

    @Id
    @Column(name = "movie_id", columnDefinition = "BINARY(16)")
    private byte[] movieIdBin;

    @Transient
    private UUID movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String cover;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Vote> votes = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (movieId != null) movieIdBin = UuidUtil.uuidToBin(movieId);
    }

    @PostLoad
    private void postLoad() {
        if (movieIdBin != null) movieId = UuidUtil.binToUUID(movieIdBin);
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
