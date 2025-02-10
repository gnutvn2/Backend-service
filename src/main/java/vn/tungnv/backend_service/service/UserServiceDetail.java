package vn.tungnv.backend_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.tungnv.backend_service.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService UserServiceDetail() {
        return userRepository::findByUsername;
    }
}
