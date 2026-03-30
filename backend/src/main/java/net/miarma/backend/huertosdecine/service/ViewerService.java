package net.miarma.backend.huertosdecine.service;

import net.miarma.backend.huertosdecine.client.CineWebClient;
import net.miarma.backend.huertosdecine.dto.StatusDto;
import net.miarma.backend.huertosdecine.dto.ViewerDto;
import net.miarma.backend.huertosdecine.mapper.UserMetadataMapper;
import net.miarma.backend.huertosdecine.model.UserMetadata;
import net.miarma.backlib.dto.UserWithCredentialDto;
import net.miarma.backlib.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ViewerService {
    private final CineWebClient webClient;
    private final UserMetadataService metadataService;

    public ViewerService(CineWebClient webClient, UserMetadataService metadataService) {
        this.webClient = webClient;
        this.metadataService = metadataService;
    }

    @Cacheable(value = "viewerById")
    public ViewerDto getById(UUID userId) {
        var uwc = webClient.getUserWithCredential(userId, (byte)3);
        if (uwc == null) {
            throw new NotFoundException("Espectador no encontrado");
        }

        var meta = metadataService.getById(userId);
        if (meta == null) {
            throw new NotFoundException("User metadata not found");
        }

        return new ViewerDto(
            uwc.user(),
            uwc.account(),
            UserMetadataMapper.toResponse(meta)
        );
    }

    @Cacheable("viewers")
    public List<ViewerDto> getAll() {
        List<UserWithCredentialDto> all = webClient.getAllUsersWithCredentials((byte)3);

        return all.stream()
            .filter(uwc -> metadataService.existsById(uwc.user().getUserId()))
            .map(uwc -> {
                var meta = metadataService.getById(uwc.user().getUserId());
                return new ViewerDto(
                        uwc.user(),
                        uwc.account(),
                        UserMetadataMapper.toResponse(meta)
                );
            })
            .toList();
    }

    @CacheEvict(value = "viewers", allEntries = true)
    public ViewerDto update(UUID userId, ViewerDto changes) {
        try {
            webClient.updateUser(userId, new UserWithCredentialDto(changes.user(), changes.account()));
            metadataService.update(userId, UserMetadataMapper.fromResponse(changes.metadata()));
        } catch (Exception e) {
            throw new RuntimeException("No se pudo actualizar el espectador");
        }
        return changes;
    }

    @CacheEvict(value = "viewers", allEntries = true)
    public StatusDto updateStatus(UUID userId, StatusDto dto) {
        try {
            webClient.updateCredentialStatus(userId, (byte)3, (byte)dto.status());
            UserMetadata metadata = metadataService.getById(userId);
            metadata.setDeactivatedAt(Instant.now());
            metadataService.update(userId, metadata);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo actualizar el espectador");
        }
    }
}
