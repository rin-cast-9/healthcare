package com.rincast.healthcare_backend.service;


import com.rincast.healthcare_backend.dto.AuthenticationResponse;
import com.rincast.healthcare_backend.dto.LoginRequest;
import com.rincast.healthcare_backend.dto.RegisterRequest;
import com.rincast.healthcare_backend.model.Role;
import com.rincast.healthcare_backend.model.User;
import com.rincast.healthcare_backend.repository.UserRepository;
import com.rincast.healthcare_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        System.out.println(user);
        System.out.println(jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

}
