package net.miarma.backend.huertosdecine.service;

import jakarta.transaction.Transactional;
import net.miarma.backend.huertosdecine.client.CineWebClient; // Tu copia del WebClient
import net.miarma.backend.huertosdecine.model.Request;
import net.miarma.backend.huertosdecine.model.UserMetadata;
import net.miarma.backlib.dto.UserWithCredentialDto;
import net.miarma.backlib.exception.BadRequestException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RequestAcceptanceService {

    private final RequestService requestService;
    private final UserMetadataService metadataService;
    private final CineWebClient cineWebClient;

    public RequestAcceptanceService(
            RequestService requestService,
            UserMetadataService metadataService,
            CineWebClient cineWebClient
    ) {
        this.requestService = requestService;
        this.metadataService = metadataService;
        this.cineWebClient = cineWebClient;
    }

    @CacheEvict(value = "viewers", allEntries = true)
    public Request acceptRequest(UUID requestId) {
        Request request = requestService.updateStatus(requestId, (byte) 1); // 1 = ACCEPTED
        if (request == null) throw new RuntimeException("Error aceptando petición");

        handleSideEffects(request);
        return request;
    }

    public Request rejectRequest(UUID requestId) {
        Request request = requestService.updateStatus(requestId, (byte) 2); // 2 = REJECTED
        if (request == null) throw new RuntimeException("Error rechazando petición");
        return request;
    }

    private void handleSideEffects(Request request) {
        handleRegister(request);
    }

    private void handleRegister(Request request) {
        UserWithCredentialDto createdUser = cineWebClient.createUser(request);

        try {
            UserMetadata userMetadata = new UserMetadata();
            userMetadata.setUserId(createdUser.user().getUserId());
            userMetadata.setRole((byte) 0);
            userMetadata.setCreatedAt(Instant.now());

            metadataService.create(userMetadata);
        } catch (Exception e) {
            cineWebClient.deleteUser(createdUser.user().getUserId());
            throw new RuntimeException("Error guardando metadata, rollback completado en el Core", e);
        }
    }
}