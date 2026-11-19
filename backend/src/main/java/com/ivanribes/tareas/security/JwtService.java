package com.ivanribes.tareas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${tareas.jwt.expiration-minutes}")
    private Long expirationMinutes;

    public String generarToken(Authentication authentication) {

        Instant ahora = Instant.now();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.replace("ROLE_", "")).toList();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("mini-tareas")
                .issuedAt(ahora)
                .expiresAt(ahora.plusSeconds(expirationMinutes*60))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return  jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public long getExpiresInSeconds() {
        return expirationMinutes * 60;
    }
}
