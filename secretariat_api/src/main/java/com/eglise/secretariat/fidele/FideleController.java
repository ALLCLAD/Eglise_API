package com.eglise.secretariat.fidele;

import com.eglise.secretariat.fidele.dto.FideleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des Fidèles", description = "Endpoints de recherche, création, modification et suppression des membres")
@RestController
@RequestMapping("/api/fideles")
public class FideleController {

    private final FideleService fideleService;

    public FideleController(FideleService fideleService) {
        this.fideleService = fideleService;
    }

    @Operation(summary = "Recherche multi-critères et pagination des fidèles", description = "Permet de filtrer par nom, prénom, quartier, statut de baptême et d'activité")
    @GetMapping
    public ResponseEntity<Page<FideleDto>> searchFideles(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String quartier,
            @RequestParam(required = false) Boolean baptise,
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 20, sort = "nom") Pageable pageable) {

        Page<FideleDto> result = fideleService.searchFideles(query, quartier, baptise, actif, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FideleDto> getFideleById(@PathVariable Long id) {
        FideleDto fidele = fideleService.getFideleById(id);
        return ResponseEntity.ok(fidele);
    }

    @PostMapping
    public ResponseEntity<FideleDto> createFidele(@Valid @RequestBody FideleDto dto) {
        FideleDto createdFidele = fideleService.createFidele(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFidele);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FideleDto> updateFidele(@PathVariable Long id, @Valid @RequestBody FideleDto dto) {
        FideleDto updatedFidele = fideleService.updateFidele(id, dto);
        return ResponseEntity.ok(updatedFidele);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFidele(@PathVariable Long id) {
        fideleService.deleteFidele(id);
        return ResponseEntity.noContent().build();
    }

}
