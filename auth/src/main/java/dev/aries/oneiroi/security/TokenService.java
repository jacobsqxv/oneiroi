package dev.aries.oneiroi.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
	private final JwtEncoder encoder;
	private final JwtDecoder decoder;

	public String generateToken(Authentication auth) {
		Instant now = Instant.now();
		List<String> authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("Aries")
				.issuedAt(now)
				.expiresAt(now.plus(4, ChronoUnit.HOURS))
				.subject(auth.getName())
				.claim("scope", authorities)
				.build();
		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public UUID validateJwt(String token) {
		Jwt jwt = decoder.decode(token);
		Instant expiry = Objects.requireNonNull(jwt.getExpiresAt());
		if (expiry.isAfter(Instant.now())) {
			throw new IllegalArgumentException("Token expired");
		}
		return UUID.fromString(jwt.getSubject());
	}
}
