package vn.tungnv.backend_service.service;

import vn.tungnv.backend_service.controller.request.SignInRequest;
import vn.tungnv.backend_service.controller.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse getAccessToken(SignInRequest request);
    TokenResponse getRefreshToken(String refreshToken);

}
