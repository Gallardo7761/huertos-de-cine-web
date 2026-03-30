package net.miarma.backend.huertosdecine.controller;

import net.miarma.backend.huertosdecine.client.CoreAuthClient;
import net.miarma.backend.huertosdecine.dto.CineLoginResponse;
import net.miarma.backend.huertosdecine.mapper.UserMetadataMapper;
import net.miarma.backend.huertosdecine.model.UserMetadata;
import net.miarma.backend.huertosdecine.service.UserMetadataService;
import net.miarma.backlib.dto.LoginRequest;
import net.miarma.backlib.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class CineAuthController {
    private final UserMetadataService metadataService;
    private final CoreAuthClient authClient;

    public CineAuthController(UserMetadataService metadataService,
                                 CoreAuthClient authClient) {
        this.metadataService = metadataService;
        this.authClient = authClient;
    }

    @PostMapping("/login")
    public ResponseEntity<CineLoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse coreResponse = authClient.login(req);
        UserMetadata metadata = metadataService.getById(coreResponse.user().getUserId());
        return ResponseEntity.ok(
            new CineLoginResponse(
                coreResponse.token(),
                coreResponse.user(),
                coreResponse.account(),
                UserMetadataMapper.toResponse(metadata)
            )
        );
    }
}
