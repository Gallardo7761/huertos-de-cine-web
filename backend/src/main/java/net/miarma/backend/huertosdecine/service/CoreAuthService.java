package net.miarma.backend.huertosdecine.service;

import net.miarma.backlib.dto.LoginRequest;
import net.miarma.backlib.dto.LoginResponse;
import net.miarma.backlib.security.CoreAuthTokenHolder;
import net.miarma.backlib.security.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class CoreAuthService {

    private final RestTemplate authRestTemplate;
    private final CoreAuthTokenHolder tokenHolder;
    private final JwtService jwtService;

    @Value("${cine.user}")
    private String username;

    @Value("${cine.password}")
    private String password;

    @Value("${core.url}")
    private String coreUrl;

    public CoreAuthService(@Qualifier("authRestTemplate") RestTemplate authRestTemplate,
                           CoreAuthTokenHolder tokenHolder,
                           JwtService jwtService) {
        this.authRestTemplate = authRestTemplate;
        this.tokenHolder = tokenHolder;
        this.jwtService = jwtService;
    }

    public synchronized String getToken() {
        if (tokenHolder.getToken() == null || tokenHolder.isExpired()) {
            refreshToken();
        }
        return tokenHolder.getToken();
    }

    private void refreshToken() {
        var req = new LoginRequest(username, password, (byte) 3);

        LoginResponse resp = authRestTemplate.postForObject(
            coreUrl + "/auth/login",
            req,
            LoginResponse.class
        );

        String token = resp.token();
        Instant exp = jwtService.getExpiration(token).toInstant();
        tokenHolder.setToken(token, exp);
    }
}
