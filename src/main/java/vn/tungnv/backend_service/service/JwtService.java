package vn.tungnv.backend_service.service;

import vn.tungnv.backend_service.common.TokenType;

import java.util.List;

public interface JwtService {
    String generateAccessToken(String username, List<String> authorities);

    String generateRefreshToken(String username, List<String> authorities);

    String extractUsername(String token, TokenType type);
}
