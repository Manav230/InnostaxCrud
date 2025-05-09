package com.example.crud.Controller;

import com.example.crud.Dao.primary.UserRepository;
import com.example.crud.Dto.JwtResponse;
import com.example.crud.Model.primary.User;
import com.example.crud.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

    @Autowired
    private Jwt jwtUtil;

    @Value("$(spring.security.oauth2.client.registration.google.client-id)")
    private String clientId;

    @Value("$(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)")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            // exchange auth code for token
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_url", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, params, Map.class);
            String idToken = (String) tokenResponse.getBody().get("id_token");

            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;
                try {
                   userDetails = userDetailsService.loadUserByUsername(email);
                }catch (Exception e) {
                    User user = new User();
                    user.setEmail(email);
                    user.setFirstName(email);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setLastName(email);
                    userRepository.save(user);

                }
                JwtResponse jwtToken = jwtUtil.generateToken(idToken);
                return ResponseEntity.ok(Collections.singletonMap("token",jwtToken));

            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
