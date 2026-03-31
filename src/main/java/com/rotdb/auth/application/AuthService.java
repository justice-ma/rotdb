package com.rotdb.auth.application;

import com.rotdb.auth.api.LoginRequest;
import com.rotdb.auth.api.LoginResult;
import com.rotdb.auth.api.RegisterRequest;
import com.rotdb.auth.api.RegisterResult;
import com.rotdb.auth.domain.User;
import com.rotdb.auth.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResult register(RegisterRequest request) {
        User user = new User();
        String normalizedEmail = validateAndNormalizeEmail(request.email());
        validatePassword(request.password());
        validateAndNormalizeUsername(request.username());

        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUsername(request.username().trim());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return new RegisterResult(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());
    }

    public LoginResult login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("No user exists with this email"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Incorrect password");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new LoginResult(token, user.getId(), user.getEmail(), user.getUsername());
    }

    private String validateAndNormalizeUsername(String username) {
        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        return trimmedUsername;
    }

    private String validateAndNormalizeEmail(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("An account already exists with that email");
        }
        return normalizedEmail;
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters");
        }

        int digits = 0;
        int chars = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isAlphabetic(c)) {
                chars++;
            }
            if (chars > 0 && digits > 0) {
                break;
            }
        }

        if (chars == 0 || digits == 0) {
            throw new IllegalArgumentException("Password must contain at least one number and one letter");
        }
    }
}
