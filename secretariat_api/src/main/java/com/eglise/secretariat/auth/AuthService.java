package com.eglise.secretariat.auth;

import com.eglise.secretariat.auth.dto.AuthResponseDto;
import com.eglise.secretariat.auth.dto.LoginRequestDto;
import com.eglise.secretariat.auth.dto.UpdateProfileDto;
import com.eglise.secretariat.shared.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service gérant la logique d'authentification et d'émission des jetons JWT.
 */
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.auth.username:secretaire}")
    private String defaultUsername = "secretaire";

    // Mot de passe par défaut pour le compte secrétariat : secretaire123
    @Value("${app.auth.password:secretaire123}")
    private String defaultPassword = "secretaire123";

    public AuthService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDto login(LoginRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // Vérification des identifiants (compte secrétariat)
        boolean isValidUsername = defaultUsername.equals(username);
        boolean isValidPassword = defaultPassword.equals(password) || passwordEncoder.matches(password, passwordEncoder.encode(defaultPassword));

        if (!isValidUsername || !isValidPassword) {
            throw new BadCredentialsException("Identifiant ou mot de passe incorrect");
        }

        // Attribution du rôle par défaut
        String role = "SECRETAIRE";

        // Génération du token JWT
        String token = jwtUtil.generateToken(username, role);

        return new AuthResponseDto(token, jwtUtil.getExpirationMs(), role);
    }

    public void updateProfile(String currentUsername, UpdateProfileDto request) {
        // 1. Vérification obligatoire du mot de passe actuel
        boolean isValidPassword = defaultPassword.equals(request.getCurrentPassword())
                || passwordEncoder.matches(request.getCurrentPassword(), passwordEncoder.encode(defaultPassword));

        if (!isValidPassword) {
            throw new BadCredentialsException("Le mot de passe actuel est incorrect");
        }

        // 2. Modification du username si renseigné
        if (request.getNewUsername() != null && !request.getNewUsername().isBlank()) {
            this.defaultUsername = request.getNewUsername();
        }

        // 3. Modification du mot de passe si renseigné
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            this.defaultPassword = request.getNewPassword();
        }
    }

}
