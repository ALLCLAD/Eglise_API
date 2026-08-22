package com.eglise.secretariat.document.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LettreRecommandationRequestDto(
        @NotNull(message = "L'ID du fidèle est obligatoire")
        Long fideleId,

        @NotBlank(message = "Le motif du départ est obligatoire")
        String motif, // ex: transfert, voyage, emploi

        @NotBlank(message = "L'église de destination est obligatoire")
        String egliseDestination,

        LocalDate dateDepart
) {}
