package com.eglise.secretariat.auth;

import com.eglise.secretariat.auth.dto.AuthResponseDto;
import com.eglise.secretariat.auth.dto.LoginRequestDto;
import com.eglise.secretariat.auth.dto.UpdateProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentification", description = "Endpoints de connexion et de gestion de profil")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Connexion d'un utilisateur", description = "Retourne un jeton JWT valide et les informations du profil")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mise à jour du profil", description = "Permet à l'utilisateur connecté de modifier son identifiant ou mot de passe")
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@Valid @RequestBody UpdateProfileDto request,
                                                java.security.Principal principal) {
        authService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok("Profil mis à jour avec succès");
    }

}
