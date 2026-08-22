package com.eglise.secretariat.fidele;

import com.eglise.model.Fidele;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FideleSpecification {

    public static Specification<Fidele> filterFideles(String query, String quartier, Boolean baptise, Boolean actif) {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Recherche textuelle globale (Nom ou Prénoms ou Téléphones)
            if (StringUtils.hasText(query)) {
                String searchPattern = "%" + query.toLowerCase() + "%";
                Predicate nomMatch = cb.like(cb.lower(root.get("nom")), searchPattern);
                Predicate prenomMatch = cb.like(cb.lower(root.get("prenoms")), searchPattern);
                Predicate telTogocelMatch = cb.like(cb.lower(root.get("adresse").get("contact_togocel")), searchPattern);
                Predicate telMoovMatch = cb.like(cb.lower(root.get("adresse").get("contact_moov")), searchPattern);
                predicates.add(cb.or(nomMatch, prenomMatch, telTogocelMatch, telMoovMatch));
            }

            // Filtre par quartier (domicile)
            if (StringUtils.hasText(quartier)) {
                predicates.add(cb.equal(cb.lower(root.get("adresse").get("domicile")), quartier.toLowerCase()));
            }

            // Filtre par statut baptisé (baptême d'eau)
            if (baptise != null && baptise) {
                predicates.add(cb.isNotNull(root.get("parcours_spirituel").get("date_bapteme_eau")));
            }

            // Filtre par membre actif
            if (actif != null) {
                predicates.add(cb.equal(root.get("statut_fidele").get("carte_membre_valide"), actif));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
