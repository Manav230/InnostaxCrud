package com.example.crud.Controller;

import com.example.crud.Dao.primary.UserRepository;
import com.example.crud.Dto.JwtResponse;
import com.example.crud.Dto.LoginRequest;
import com.example.crud.Dto.SignupRequest;
import com.example.crud.Model.primary.User;
import com.example.crud.security.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jwt jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (userRepository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setFirstName(request.firstName);
        user.setLastName(request.lastName);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password)
        );

        JwtResponse tokenResponse = jwtUtil.generateToken(request.email);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestBody JwtResponse request) {
        boolean isValid = jwtUtil.validateToken(request.getToken(), jwtUtil.extractUsername(request.getToken()));
        return ResponseEntity.ok(isValid ? "Token is valid" : "Token is invalid or expired");
    }
}
