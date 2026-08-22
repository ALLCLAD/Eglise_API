package com.eglise.secretariat.fidele;

import com.eglise.model.Fidele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FideleRepository extends JpaRepository<Fidele, Long>, JpaSpecificationExecutor<Fidele> {

    @Query("SELECT f FROM Fidele f WHERE f.adresse.email = :email")
    Optional<Fidele> findByEmail(@Param("email") String email);

    @Query("SELECT f FROM Fidele f WHERE f.adresse.contact_togocel = :tel OR f.adresse.contact_moov = :tel")
    Optional<Fidele> findByTelephone(@Param("tel") String telephone);
}
