package net.miarma.backend.huertosdecine.controller;

import net.miarma.backend.huertosdecine.dto.MovieDto;
import net.miarma.backend.huertosdecine.dto.VoteDto;
import net.miarma.backend.huertosdecine.mapper.MovieMapper;
import net.miarma.backend.huertosdecine.mapper.VoteMapper;
import net.miarma.backend.huertosdecine.model.Movie;
import net.miarma.backend.huertosdecine.model.Vote;
import net.miarma.backend.huertosdecine.service.MovieService;
import net.miarma.backend.huertosdecine.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService service;
    private final VoteService voteService;

    public MovieController(MovieService service, VoteService voteService) {
        this.service = service;
        this.voteService = voteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CINE_ROLE_ADMIN', 'CINE_ROLE_DEV')")
    public ResponseEntity<MovieDto.Response> create(@RequestBody MovieDto.Request request) {
        Movie entity = MovieMapper.toEntity(request);
        return ResponseEntity.ok(MovieMapper.toResponse(service.create(entity)));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto.Response>> getAll(
            @RequestHeader(value = "X-User-Id", required = false) UUID userId
    ) {
        return ResponseEntity.ok(service.getAllWithVotes(userId));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto.Response> getById(
            @PathVariable("movieId") UUID movieId,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId
    ) {
        MovieDto.Response details = service.getMovieWithVotes(movieId, userId);
        return ResponseEntity.ok(details);
    }

    @PostMapping("/{movieId}/upvote")
    public ResponseEntity<VoteDto.Response> upvote(
        @PathVariable("movieId") UUID movieId,
        @RequestHeader("X-User-Id") UUID userId
    ) {
        VoteDto.Request req = new VoteDto.Request();
        req.setUserId(userId);
        req.setMovieId(movieId);
        req.setVote((byte) 1);
        Vote vote = service.vote(VoteMapper.toEntity(req));
        return ResponseEntity.ok(VoteMapper.toResponse(vote));
    }

    @PostMapping("/{movieId}/downvote")
    public ResponseEntity<VoteDto.Response> downvote(
        @PathVariable("movieId") UUID movieId,
        @RequestHeader("X-User-Id") UUID userId
    ) {
        VoteDto.Request req = new VoteDto.Request();
        req.setUserId(userId);
        req.setMovieId(movieId);
        req.setVote((byte) -1);
        Vote vote = service.vote(VoteMapper.toEntity(req));
        return ResponseEntity.ok(VoteMapper.toResponse(vote));
    }

    @PutMapping("/{movieId}")
    @PreAuthorize("hasAnyRole('CINE_ROLE_ADMIN', 'CINE_ROLE_DEV')")
    public ResponseEntity<MovieDto.Response> update(
            @PathVariable("movieId") UUID movieId,
            @RequestBody MovieDto.Request changes
    ) {
        Movie entityChanges = MovieMapper.toEntity(changes);
        Movie updated = service.update(movieId, entityChanges);
        return ResponseEntity.ok(MovieMapper.toResponse(updated));
    }

    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasAnyRole('CINE_ROLE_ADMIN', 'CINE_ROLE_DEV')")
    public ResponseEntity<Map<String,String>> delete(
            @PathVariable("movieId") UUID movieId
    ) {
        service.delete(movieId);
        return ResponseEntity.ok(Map.of("message", "Se ha eliminado la película con id: " + movieId));
    }
}