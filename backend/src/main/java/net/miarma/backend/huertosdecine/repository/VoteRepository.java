package net.miarma.backend.huertosdecine.repository;

import net.miarma.backend.huertosdecine.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, byte[]> {
    @Query("""
        SELECT v FROM Vote v
        WHERE v.userIdBin = :userId AND v.movieIdBin = :movieId
    """)
    Optional<Vote> findExistingVote(@Param("userId") byte[] userId,
                                    @Param("movieId") byte[] movieId);

    @Query("""
        SELECT v FROM Vote v
        WHERE v.movieIdBin = :movieId
    """)
    List<Vote> findByMovieId(@Param("movieId") byte[] moveId);
}
