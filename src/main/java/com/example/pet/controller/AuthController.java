package com.example.pet.controller;

import com.example.pet.models.NguoiDung;
import com.example.pet.repository.NguoiDungRepository;
import com.example.pet.util.CustomUserDetails;
import com.example.pet.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getLoaind(),
                userDetails.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.toList())
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NguoiDung nguoiDung) {
        if (nguoiDungRepository.findByEmail(nguoiDung.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        nguoiDung.setMatkhau(passwordEncoder.encode(nguoiDung.getMatkhau()));
        nguoiDungRepository.save(nguoiDung);
        return ResponseEntity.status(201).body("User registered successfully");
    }
}

class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class AuthResponse {
    private String token;
    private String email;
    private int loaind;
    private List<String> roles;

    public AuthResponse(String token, String email, int loaind, List<String> roles) {
        this.token = token;
        this.email = email;
        this.loaind = loaind;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public int getLoaind() {
        return loaind;
    }

    public List<String> getRoles() {
        return roles;
    }
}