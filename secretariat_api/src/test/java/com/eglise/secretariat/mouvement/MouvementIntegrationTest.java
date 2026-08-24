package com.eglise.secretariat.mouvement;

import com.eglise.model.AdresseCoordonnees;
import com.eglise.model.Fidele;
import com.eglise.model.StatutFidele;
import com.eglise.model.enumeration.Sexe;
import com.eglise.secretariat.fidele.FideleRepository;
import com.eglise.secretariat.fidele.dto.FideleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class MouvementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FideleRepository fideleRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Fidele savedFidele;

    @BeforeEach
    void setUp() {
        Fidele fidele = new Fidele();
        fidele.setNom("LOGOU");
        fidele.setPrenoms("Emmanuel");
        fidele.setDateNaissance(LocalDate.of(1990, 8, 15));
        fidele.setSexe(Sexe.MASCULIN);

        AdresseCoordonnees adresse = new AdresseCoordonnees();
        adresse.setDomicile("Adidogomé");
        fidele.setAdresse(adresse);

        StatutFidele statut = new StatutFidele();
        statut.setCarteMembreValide(false);
        statut.setCarnetDimeValide(false);
        fidele.setStatutFidele(statut);

        savedFidele = fideleRepository.save(fidele);
    }

    @Test
    @WithMockUser(username = "test-secretaire", roles = {"SECRETAIRE"})
    void testUpdateCarteMembreStatus() throws Exception {
        mockMvc.perform(patch("/api/mouvements/carte-membre/" + savedFidele.getId())
                        .param("valide", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Fidele updated = fideleRepository.findById(savedFidele.getId()).orElseThrow();
        assertTrue(updated.getStatutFidele().getCarteMembreValide());

        // Check if movement log has been created
        long count = mouvementRepository.count();
        assertTrue(count > 0);
    }

    @Test
    @WithMockUser(username = "test-secretaire", roles = {"SECRETAIRE"})
    void testUpdateCarnetDimeStatus() throws Exception {
        mockMvc.perform(patch("/api/mouvements/carnet-dime/" + savedFidele.getId())
                        .param("valide", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Fidele updated = fideleRepository.findById(savedFidele.getId()).orElseThrow();
        assertTrue(updated.getStatutFidele().getCarnetDimeValide());
    }

    @Test
    @WithMockUser(username = "test-secretaire", roles = {"SECRETAIRE"})
    void testRegisterFideleEntrant() throws Exception {
        FideleDto dto = new FideleDto();
        dto.setNom("AKAKPO");
        dto.setPrenoms("Koffi");
        dto.setSexe(Sexe.MASCULIN);
        dto.setTelephone("90123456");
        dto.setQuartier("Zanguera");
        dto.setLettreRecommandationPresentee(true);
        dto.setPasteurLettreRecommandation("Pasteur APEDO");
        dto.setEgliseLettreRecommandation("AD Kpalimé");
        dto.setDateLettreRecommandation(LocalDate.now());

        mockMvc.perform(post("/api/mouvements/fidele-entrant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Verify it exists in database
        Optional<Fidele> created = fideleRepository.findAll().stream()
                .filter(f -> "AKAKPO".equals(f.getNom()))
                .findFirst();

        assertTrue(created.isPresent());
        assertEquals("Pasteur APEDO", created.get().getLettreEntrante().getNomPasteurSignataire());
    }
}
