package net.miarma.backend.huertosdecine.service;

import net.miarma.backend.huertosdecine.dto.UserMetadataDto;
import net.miarma.backend.huertosdecine.mapper.UserMetadataMapper;
import net.miarma.backend.huertosdecine.model.UserMetadata;
import net.miarma.backend.huertosdecine.repository.UserMetadataRepository;
import net.miarma.backlib.exception.NotFoundException;
import net.miarma.backlib.util.UuidUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserMetadataService {
    private final UserMetadataRepository repository;

    public UserMetadataService(UserMetadataRepository repository) { this.repository = repository; }

    @CacheEvict(
            value = {
                    "metadataByUserId",
                    "metadataExists"
            },
            key = "#p0"
    )
    public UserMetadata create(UserMetadata metadata) {
        if (metadata.getUserId() == null) metadata.setUserId(UUID.randomUUID());
        return repository.save(metadata);
    }

    @Cacheable("metadataByUserId")
    public UserMetadata getById(UUID userId) {
        byte[] idBytes = UuidUtil.uuidToBin(userId);
        return repository.findById(idBytes)
                .orElseThrow(() -> new NotFoundException("Metadatos de usuario no encontrados"));
    }

    @Cacheable("metadataExists")
    public boolean existsById(UUID userId) {
        byte[] idBytes = UuidUtil.uuidToBin(userId);
        return repository.existsById(idBytes);
    }

    @CacheEvict(
        value = {
            "metadataByUserId",
            "metadataExists"
        },
        key = "#p0"
    )
    public UserMetadata update(UUID userId, UserMetadata changes) {
        byte[] idBytes = UuidUtil.uuidToBin(userId);

        UserMetadata metadata = repository.findById(idBytes)
                .orElseThrow(() -> new NotFoundException("Metadatos de usuario no encontrados"));

        if (changes.getRole() != null) metadata.setRole(changes.getRole());
        if (changes.getDeactivatedAt() != null) metadata.setDeactivatedAt(changes.getDeactivatedAt());

        return repository.save(metadata);
    }
}