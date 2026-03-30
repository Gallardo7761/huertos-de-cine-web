package net.miarma.backend.huertosdecine.service;

import net.miarma.backend.huertosdecine.dto.MovieDto;
import net.miarma.backend.huertosdecine.mapper.MovieMapper;
import net.miarma.backend.huertosdecine.model.Movie;
import net.miarma.backend.huertosdecine.model.Vote;
import net.miarma.backend.huertosdecine.repository.MovieRepository;
import net.miarma.backlib.exception.NotFoundException;
import net.miarma.backlib.util.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final VoteService voteService;

    public MovieService(MovieRepository repository, VoteService voteService) {
        this.repository = repository;
        this.voteService = voteService;
    }

    private MovieDto.Response calculateVotes(Movie movie, UUID userId) {
        List<Vote> votes = movie.getVotes();

        long ups = votes.stream().filter(v -> v.getVote() == (byte)1).count();
        long downs = votes.stream().filter(v -> v.getVote() == (byte)-1).count();

        Byte currentUserVote = (userId == null) ? (byte)0 : votes.stream()
                .filter(v -> v.getUserId() != null && v.getUserId().equals(userId))
                .map(Vote::getVote)
                .findFirst()
                .orElse((byte) 0);

        MovieDto.Response dto = MovieMapper.toResponse(movie);
        dto.setUpvotes(ups);
        dto.setDownvotes(downs);
        dto.setUserVote(currentUserVote);

        return dto;
    }

    public Movie create(Movie movie) {
        movie.setMovieId(UUID.randomUUID());
        return repository.save(movie);
    }

    public List<MovieDto.Response> getAllWithVotes(UUID userId) {
        List<Movie> movies = repository.findAll();

        return movies.stream()
                .map(movie -> calculateVotes(movie, userId))
                .collect(Collectors.toList());
    }

    public MovieDto.Response getMovieWithVotes(UUID movieId, UUID userId) {
        Movie movie = getById(movieId);
        return calculateVotes(movie, userId);
    }

    public Movie getById(UUID movieId) {
        byte[] idBytes = UuidUtil.uuidToBin(movieId);
        return repository.findById(idBytes)
                .orElseThrow(() -> new NotFoundException("Película no encontrada"));
    }

    public Vote vote(Vote vote) {
        return voteService.create(vote);
    }

    public Movie update(UUID movieId, Movie changes) {
        Movie existingMovie = getById(movieId);

        existingMovie.setTitle(changes.getTitle());
        existingMovie.setDescription(changes.getDescription());
        existingMovie.setCover(changes.getCover());

        return repository.save(existingMovie);
    }

    public void delete(UUID movieId) {
        repository.deleteById(UuidUtil.uuidToBin(movieId));
    }
}