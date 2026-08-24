package com.eglise.secretariat.mouvement;

import com.eglise.model.Fidele;
import com.eglise.model.StatutFidele;
import com.eglise.secretariat.fidele.FideleRepository;
import com.eglise.secretariat.fidele.FideleService;
import com.eglise.secretariat.fidele.dto.FideleDto;
import com.eglise.secretariat.mouvement.dto.ConformiteStatusDto;
import com.eglise.secretariat.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MouvementService {

    private final MouvementRepository mouvementRepository;
    private final FideleRepository fideleRepository;
    private final FideleService fideleService;

    public MouvementService(MouvementRepository mouvementRepository, FideleRepository fideleRepository, FideleService fideleService) {
        this.mouvementRepository = mouvementRepository;
        this.fideleRepository = fideleRepository;
        this.fideleService = fideleService;
    }

    public ConformiteStatusDto updateCarteMembreStatus(Long fideleId, Boolean status) {
        Fidele fidele = fideleRepository.findById(fideleId)
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + fideleId));

        StatutFidele statutFidele = fidele.getStatutFidele();
        if (statutFidele == null) {
            statutFidele = new StatutFidele();
            fidele.setStatutFidele(statutFidele);
        }
        statutFidele.setCarteMembreValide(status);
        fideleRepository.save(fidele);

        LocalDateTime now = LocalDateTime.now();
        Mouvement mouvement = new Mouvement(fidele, "CARTE_MEMBRE", status, now);
        mouvementRepository.save(mouvement);

        return new ConformiteStatusDto(
                fidele.getId(),
                statutFidele.getCarteMembreValide(),
                statutFidele.getCarnetDimeValide(),
                statutFidele.estEnRegle(),
                now
        );
    }

    public ConformiteStatusDto updateCarnetDimeStatus(Long fideleId, Boolean status) {
        Fidele fidele = fideleRepository.findById(fideleId)
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + fideleId));

        StatutFidele statutFidele = fidele.getStatutFidele();
        if (statutFidele == null) {
            statutFidele = new StatutFidele();
            fidele.setStatutFidele(statutFidele);
        }
        statutFidele.setCarnetDimeValide(status);
        fideleRepository.save(fidele);

        LocalDateTime now = LocalDateTime.now();
        Mouvement mouvement = new Mouvement(fidele, "CARNET_DIME", status, now);
        mouvementRepository.save(mouvement);

        return new ConformiteStatusDto(
                fidele.getId(),
                statutFidele.getCarteMembreValide(),
                statutFidele.getCarnetDimeValide(),
                statutFidele.estEnRegle(),
                now
        );
    }

    public FideleDto saveFideleEntrant(FideleDto dto) {
        dto.setLettreRecommandationPresentee(true);
        if (dto.getDateLettreRecommandation() == null) {
            dto.setDateLettreRecommandation(java.time.LocalDate.now());
        }
        return fideleService.createFidele(dto);
    }
}
