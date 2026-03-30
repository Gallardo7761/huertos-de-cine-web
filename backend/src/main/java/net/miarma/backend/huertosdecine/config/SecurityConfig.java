package net.miarma.backend.huertosdecine.config;

import net.miarma.backend.huertosdecine.security.CineJwtFilter;
import net.miarma.backlib.http.RestAccessDeniedHandler;
import net.miarma.backlib.http.RestAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CineJwtFilter jwtFilter;
    private final RestAuthEntryPoint authEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            CineJwtFilter jwtFilter,
            RestAuthEntryPoint authEntryPoint,
            RestAccessDeniedHandler accessDeniedHandler,
            Optional<CorsConfigurationSource> corsConfigurationSource
    ) {
        this.jwtFilter = jwtFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.corsConfigurationSource = corsConfigurationSource.orElse(null);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (corsConfigurationSource != null) {
            http.cors(Customizer.withDefaults());
        }

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .authorizeHttpRequests(auth -> auth
                    // PUBLICAS
                    //.requestMatchers("/movies/*/upvote", "/movies/*/downvote").authenticated()
                    // PRIVADAS
                    .anyRequest().permitAll()
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


