package vn.tungnv.backend_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.tungnv.backend_service.common.TokenType;
import vn.tungnv.backend_service.controller.request.SignInRequest;
import vn.tungnv.backend_service.controller.response.TokenResponse;
import vn.tungnv.backend_service.exception.EntityExistException;
import vn.tungnv.backend_service.exception.ForBiddenException;
import vn.tungnv.backend_service.model.User;
import vn.tungnv.backend_service.repository.UserRepository;
import vn.tungnv.backend_service.service.AuthenticationService;
import vn.tungnv.backend_service.service.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION_SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("Get access token");

        //Kiem tra user co hop le hay khong. Neu khong hop le thi khong tra ve token ma se tra ve loi
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken
                                    (request.getUsername(), request.getPassword())
                    );

            //Luu user
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            log.error("Login fail, message={}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        var user = userRepository.findByUsername(request.getUsername());

        //Kiem tra truong hop van tra ve token nhung user khong ton tai
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), request.getUsername(), user.getAuthorities());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), request.getUsername(), user.getAuthorities());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
        log.info("Get refresh token");

        if (!StringUtils.hasLength(refreshToken)) {
            throw new EntityExistException("Token must be not blank");
        }

        try {
            // Verify token
            String userName = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
            log.info("Refresh token found, username={}", userName);

            // check user is active or inactivated
            User user = userRepository.findByUsername(userName);

            // generate new access token
            String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities());

            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            log.error("Access denied! errorMessage: {}", e.getMessage(), e.getCause());
            throw new ForBiddenException(e.getMessage());
        }
    }
}
