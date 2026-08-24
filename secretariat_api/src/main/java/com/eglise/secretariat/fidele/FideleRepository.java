package com.eglise.secretariat.fidele;

import com.eglise.model.Fidele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FideleRepository extends JpaRepository<Fidele, Long>, JpaSpecificationExecutor<Fidele> {

    @Query("SELECT f FROM Fidele f WHERE f.adresse.email = :email")
    Optional<Fidele> findByEmail(@Param("email") String email);

    @Query("SELECT f FROM Fidele f WHERE f.adresse.contact_togocel = :tel OR f.adresse.contact_moov = :tel")
    Optional<Fidele> findByTelephone(@Param("tel") String telephone);

    @Query("SELECT COUNT(f) FROM Fidele f")
    long countTotalFideles();

    @Query("SELECT COUNT(f) FROM Fidele f WHERE f.statut_fidele.carte_membre_valide = true")
    long countFidelesActifs();

    @Query("SELECT COUNT(f) FROM Fidele f WHERE f.parcours_spirituel.date_bapteme_eau IS NOT NULL")
    long countFidelesBaptises();

    @Query("SELECT f.adresse.domicile, COUNT(f) FROM Fidele f GROUP BY f.adresse.domicile")
    List<Object[]> countFidelesByQuartier();

    @Query("SELECT f.statut_fidele.paye_dime, COUNT(f) FROM Fidele f GROUP BY f.statut_fidele.paye_dime")
    List<Object[]> countFidelesByPayeDime();

    @Query("SELECT f FROM Fidele f LEFT JOIN FETCH f.statut_fidele LEFT JOIN FETCH f.adresse")
    java.util.List<Fidele> findAllForDashboard();
}
