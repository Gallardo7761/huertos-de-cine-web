package net.miarma.backend.huertosdecine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Arrays;

@Embeddable
public class VoteId implements Serializable {
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private byte[] userIdBin;

    @Column(name = "movie_id", columnDefinition = "BINARY(16)")
    private byte[] movieIdBin;

    public VoteId() {}

    public VoteId(byte[] userIdBin, byte[] movieIdBin) {
        this.userIdBin = userIdBin;
        this.movieIdBin = movieIdBin;
    }

    public byte[] getUserIdBin() {
        return userIdBin;
    }

    public void setUserIdBin(byte[] userIdBin) {
        this.userIdBin = userIdBin;
    }

    public byte[] getMovieIdBin() {
        return movieIdBin;
    }

    public void setMovieIdBin(byte[] movieIdBin) {
        this.movieIdBin = movieIdBin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        VoteId voteId = (VoteId) o;
        return Arrays.equals(userIdBin, voteId.userIdBin) && Arrays.equals(movieIdBin, voteId.movieIdBin);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(userIdBin);
        result = 31 * result + Arrays.hashCode(movieIdBin);
        return result;
    }
}
