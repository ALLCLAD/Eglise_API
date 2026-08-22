package com.eglise.secretariat.auth;

import com.eglise.secretariat.auth.dto.AuthResponseDto;
import com.eglise.secretariat.auth.dto.LoginRequestDto;
import com.eglise.secretariat.shared.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(jwtUtil, passwordEncoder);
    }

    @Test
    void testLoginSuccess() {
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("mocked-jwt-token");
        when(jwtUtil.getExpirationMs()).thenReturn(86400000L);

        LoginRequestDto request = new LoginRequestDto("secretaire", "secretaire123");
        AuthResponseDto response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("SECRETAIRE", response.getRole());
    }

    @Test
    void testLoginBadCredentials() {
        LoginRequestDto request = new LoginRequestDto("secretaire", "mauvais_mot_de_passe");
        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }
}
