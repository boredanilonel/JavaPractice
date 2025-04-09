package com.example.shop.service;

import com.example.shop.model.Role;
import com.example.shop.model.Token;
import com.example.shop.model.User;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.TokenRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenRepository tokenRepository,
                       JwtUtil jwtUtil,
                       RoleRepository roleRepository,
                       AuthenticationConfiguration configuration) throws Exception {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = configuration.getAuthenticationManager();
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtUtil.generateToken(user);

        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(LocalDateTime.now().plusHours(1));
        tokenRepository.save(token);

        return jwt;
    }

    public User registerAdmin(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        User admin = new User(username, passwordEncoder.encode(password));
        admin.setRoles(Set.of(adminRole));

        return userRepository.save(admin);
    }


    public User register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        User user = new User(username, passwordEncoder.encode(password));
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }
}
