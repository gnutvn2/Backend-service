package vn.tungnv.backend_service.service;

import org.springframework.security.core.GrantedAuthority;
import vn.tungnv.backend_service.common.TokenType;

import java.util.Collection;

public interface JwtService {
    String generateAccessToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String generateRefreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String extractUsername(String token, TokenType type);
}
