package com.eglise.secretariat.dashboard;

import com.eglise.model.Fidele;
import com.eglise.secretariat.dashboard.dto.DashboardStatsDto;
import com.eglise.secretariat.fidele.FideleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final FideleRepository fideleRepository;

    public DashboardService(FideleRepository fideleRepository) {
        this.fideleRepository = fideleRepository;
    }

    public DashboardStatsDto getDashboardStats() {
        List<Fidele> fideles = fideleRepository.findAllForDashboard();

        long totalInscrits = fideles.size();
        Map<String, Long> repartitionParQuartier = new HashMap<>();
        Map<String, Long> fluxMensuels = new TreeMap<>(); // Utiliser TreeMap pour trier par mois chronologiquement
        long dimePayeCount = 0;

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Fidele f : fideles) {
            // 1. Quartier
            String quartier = "Inconnu";
            if (f.getAdresse() != null && f.getAdresse().getDomicile() != null && !f.getAdresse().getDomicile().trim().isEmpty()) {
                quartier = f.getAdresse().getDomicile().trim();
            }
            repartitionParQuartier.put(quartier, repartitionParQuartier.getOrDefault(quartier, 0L) + 1);

            // 2. Dîmes
            if (f.getStatutFidele() != null && Boolean.TRUE.equals(f.getStatutFidele().getPayeDime())) {
                dimePayeCount++;
            }

            // 3. Flux mensuels
            LocalDate dateInsc = f.getDateInscription();
            if (dateInsc == null && f.getStatutFidele() != null) {
                // Essayer de récupérer de la date d'intégration si inscription est null
                dateInsc = f.getStatutFidele().getEstMembreActifDepuis();
            }
            if (dateInsc == null) {
                dateInsc = LocalDate.now(); // Fallback
            }
            String moisStr = dateInsc.format(monthFormatter);
            fluxMensuels.put(moisStr, fluxMensuels.getOrDefault(moisStr, 0L) + 1);
        }

        double tauxDime = totalInscrits > 0 ? ((double) dimePayeCount * 100.0) / totalInscrits : 0.0;

        // Limiter le taux à deux décimales
        tauxDime = Math.round(tauxDime * 100.0) / 100.0;

        return new DashboardStatsDto(totalInscrits, repartitionParQuartier, tauxDime, fluxMensuels);
    }
}
