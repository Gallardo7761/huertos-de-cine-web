package net.miarma.backend.huertosdecine.service;

import net.miarma.backend.huertosdecine.dto.VoteDto;
import net.miarma.backend.huertosdecine.mapper.VoteMapper;
import net.miarma.backend.huertosdecine.model.Vote;
import net.miarma.backend.huertosdecine.repository.VoteRepository;
import net.miarma.backlib.exception.NotFoundException;
import net.miarma.backlib.util.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoteService {
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) { this.repository = repository; }

    public Vote create(Vote entity) {
        return repository.findExistingVote(
                UuidUtil.uuidToBin(entity.getUserId()),
                UuidUtil.uuidToBin(entity.getMovieId())
            )
            .map(existingVote -> {
                if (existingVote.getVote().equals(entity.getVote())) {
                    repository.delete(existingVote);
                    Vote neutral = new Vote();
                    neutral.setVote((byte) 0);
                    return neutral;
                } else {
                    existingVote.setVote(entity.getVote());
                    return repository.save(existingVote);
                }
            })
            .orElseGet(() -> {
                return repository.save(entity);
            });
    }

    public List<Vote> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    public List<Vote> getByMovieId(UUID movieId) {
        return repository.findByMovieId(UuidUtil.uuidToBin(movieId));
    }

    public Vote getSpecific(UUID userId, UUID movieId) {
        return repository.findExistingVote(UuidUtil.uuidToBin(userId), UuidUtil.uuidToBin(movieId))
                .orElseThrow(() -> new NotFoundException("Voto no encontrado"));
    }
}