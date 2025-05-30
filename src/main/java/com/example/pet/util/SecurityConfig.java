package com.example.pet.util;

import com.example.pet.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF vì sử dụng JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/api/sanpham/**","/api/danhmuc/**","/api/giohang/add"
                        ,"/api/giohang/user/**","/api/giohang/xoa-theo-nguoidung/**","/api/chi-tieu/**","/api/giohang/**"
                                ,"/api/don-hang/**","/api/loai/**","/api/nguoidung/**"
                                ,"/api/sanpham/up**","/api/van-chuyen/**","/api/van-chuyen/nguoidung/**"
                        ).permitAll()
                        .requestMatchers("/api/danhmuc/**", "/api/loai/**", "/api/sanpham/**"
                        ).hasRole("ADMIN") // Chỉ admin truy cập
                        .anyRequest().authenticated() // Các request khác cần xác thực
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không sử dụng session
                );

        // Thêm JwtRequestFilter trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}