package com.eglise.secretariat.fidele;

import com.eglise.model.AdresseCoordonnees;
import com.eglise.model.Fidele;
import com.eglise.model.ParcoursSpirituel;
import com.eglise.model.StatutFidele;
import com.eglise.model.enumeration.Sexe;
import com.eglise.secretariat.fidele.dto.FideleDto;
import com.eglise.secretariat.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FideleService {

    private final FideleRepository fideleRepository;

    public FideleService(FideleRepository fideleRepository) {
        this.fideleRepository = fideleRepository;
    }

    public Page<FideleDto> searchFideles(String query, String quartier, Boolean baptise, Boolean actif, Pageable pageable) {
        Specification<Fidele> spec = FideleSpecification.filterFideles(query, quartier, baptise, actif);
        return fideleRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    public FideleDto getFideleById(Long id) {
        Fidele fidele = fideleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + id));
        return mapToDto(fidele);
    }

    public FideleDto createFidele(FideleDto dto) {
        Fidele fidele = mapToEntity(dto);
        Fidele savedFidele = fideleRepository.save(fidele);
        return mapToDto(savedFidele);
    }

    public FideleDto updateFidele(Long id, FideleDto dto) {
        Fidele fidele = fideleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + id));

        fidele.setNom(dto.getNom());
        fidele.setPrenoms(dto.getPrenom());
        if (dto.getSexe() != null) {
            try {
                fidele.setSexe(Sexe.valueOf(dto.getSexe().toUpperCase()));
            } catch (IllegalArgumentException ignored) {}
        }
        fidele.setDateNaissance(dto.getDateNaissance());
        fidele.setLieuNaissance(dto.getLieuNaissance());
        fidele.setProfession(dto.getProfession());

        // Mise à jour de l'adresse
        AdresseCoordonnees adresse = fidele.getAdresse() != null ? fidele.getAdresse() : new AdresseCoordonnees();
        adresse.setContactTogocel(dto.getTelephone());
        adresse.setEmail(dto.getEmail());
        adresse.setDomicile(dto.getQuartier());
        fidele.setAdresse(adresse);

        // Mise à jour du statut spirituel
        ParcoursSpirituel parcours = fidele.getParcoursSpirituel() != null ? fidele.getParcoursSpirituel() : new ParcoursSpirituel();
        parcours.setDateBaptemeEau(dto.getDateBapteme());
        parcours.setEgliseBaptemeEau(dto.getLieuBapteme());
        fidele.setParcoursSpirituel(parcours);

        Fidele updatedFidele = fideleRepository.save(fidele);
        return mapToDto(updatedFidele);
    }

    public void deleteFidele(Long id) {
        if (!fideleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + id);
        }
        fideleRepository.deleteById(id);
    }

    // Mapping Entité -> DTO
    private FideleDto mapToDto(Fidele fidele) {
        FideleDto dto = new FideleDto();
        dto.setId(fidele.getId());
        dto.setNom(fidele.getNom());
        dto.setPrenom(fidele.getPrenoms());
        dto.setSexe(fidele.getSexe() != null ? fidele.getSexe().name() : null);
        dto.setDateNaissance(fidele.getDateNaissance());
        dto.setLieuNaissance(fidele.getLieuNaissance());
        dto.setProfession(fidele.getProfession());

        if (fidele.getAdresse() != null) {
            dto.setTelephone(fidele.getAdresse().getContactTogocel() != null ? fidele.getAdresse().getContactTogocel() : fidele.getAdresse().getContactMoov());
            dto.setEmail(fidele.getAdresse().getEmail());
            dto.setQuartier(fidele.getAdresse().getDomicile());
        }

        if (fidele.getParcoursSpirituel() != null) {
            dto.setBaptise(fidele.getParcoursSpirituel().getDateBaptemeEau() != null);
            dto.setDateBapteme(fidele.getParcoursSpirituel().getDateBaptemeEau());
            dto.setLieuBapteme(fidele.getParcoursSpirituel().getEgliseBaptemeEau());
        }

        if (fidele.getStatutFidele() != null) {
            dto.setActif(Boolean.TRUE.equals(fidele.getStatutFidele().getCarteMembreValide()));
        }

        return dto;
    }

    // Mapping DTO -> Entité
    private Fidele mapToEntity(FideleDto dto) {
        Fidele fidele = new Fidele();
        fidele.setId(dto.getId());
        fidele.setNom(dto.getNom());
        fidele.setPrenoms(dto.getPrenom());
        if (dto.getSexe() != null) {
            try {
                fidele.setSexe(Sexe.valueOf(dto.getSexe().toUpperCase()));
            } catch (IllegalArgumentException ignored) {}
        }
        fidele.setDateNaissance(dto.getDateNaissance());
        fidele.setLieuNaissance(dto.getLieuNaissance());
        fidele.setProfession(dto.getProfession());

        AdresseCoordonnees adresse = new AdresseCoordonnees();
        adresse.setContactTogocel(dto.getTelephone());
        adresse.setEmail(dto.getEmail());
        adresse.setDomicile(dto.getQuartier());
        fidele.setAdresse(adresse);

        ParcoursSpirituel parcours = new ParcoursSpirituel();
        parcours.setDateBaptemeEau(dto.getDateBapteme());
        parcours.setEgliseBaptemeEau(dto.getLieuBapteme());
        fidele.setParcoursSpirituel(parcours);

        StatutFidele statut = new StatutFidele();
        statut.setCarteMembreValide(dto.isActif());
        fidele.setStatutFidele(statut);

        return fidele;
    }

}
