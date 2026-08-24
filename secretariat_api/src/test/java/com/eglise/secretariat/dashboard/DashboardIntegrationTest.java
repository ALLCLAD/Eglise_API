package com.eglise.secretariat.dashboard;

import com.eglise.model.AdresseCoordonnees;
import com.eglise.model.Fidele;
import com.eglise.model.StatutFidele;
import com.eglise.model.enumeration.Sexe;
import com.eglise.secretariat.fidele.FideleRepository;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DashboardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FideleRepository fideleRepository;

    @BeforeEach
    void setUp() {
        fideleRepository.deleteAll();

        // Fidèle 1: Adidogomé, Dîme payée
        Fidele f1 = new Fidele();
        f1.setNom("AMOUZOU");
        f1.setPrenoms("Kofi");
        f1.setSexe(Sexe.MASCULIN);
        f1.setDateInscription(LocalDate.of(2026, 8, 1));
        AdresseCoordonnees a1 = new AdresseCoordonnees();
        a1.setDomicile("Adidogomé");
        f1.setAdresse(a1);
        StatutFidele s1 = new StatutFidele();
        s1.setPayeDime(true);
        f1.setStatutFidele(s1);
        fideleRepository.save(f1);

        // Fidèle 2: Adidogomé, Dîme non payée
        Fidele f2 = new Fidele();
        f2.setNom("TOSSOU");
        f2.setPrenoms("Abla");
        f2.setSexe(Sexe.FEMININ);
        f2.setDateInscription(LocalDate.of(2026, 8, 10));
        AdresseCoordonnees a2 = new AdresseCoordonnees();
        a2.setDomicile("Adidogomé");
        f2.setAdresse(a2);
        StatutFidele s2 = new StatutFidele();
        s2.setPayeDime(false);
        f2.setStatutFidele(s2);
        fideleRepository.save(f2);

        // Fidèle 3: Zanguera, Dîme payée
        Fidele f3 = new Fidele();
        f3.setNom("GADO");
        f3.setPrenoms("Yao");
        f3.setSexe(Sexe.MASCULIN);
        f3.setDateInscription(LocalDate.of(2026, 7, 20));
        AdresseCoordonnees a3 = new AdresseCoordonnees();
        a3.setDomicile("Zanguera");
        f3.setAdresse(a3);
        StatutFidele s3 = new StatutFidele();
        s3.setPayeDime(true);
        f3.setStatutFidele(s3);
        fideleRepository.save(f3);
    }

    @Test
    @WithMockUser(username = "test-secretaire", roles = {"SECRETAIRE"})
    void testGetDashboardStats() throws Exception {
        mockMvc.perform(get("/api/dashboard/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalInscrits", is(3)))
                .andExpect(jsonPath("$.repartitionParQuartier.Adidogomé", is(2)))
                .andExpect(jsonPath("$.repartitionParQuartier.Zanguera", is(1)))
                .andExpect(jsonPath("$.tauxMembresAJourCotisationDime", is(66.67)))
                .andExpect(jsonPath("$.fluxMensuels.2026-08", is(2)))
                .andExpect(jsonPath("$.fluxMensuels.2026-07", is(1)));
    }
}
