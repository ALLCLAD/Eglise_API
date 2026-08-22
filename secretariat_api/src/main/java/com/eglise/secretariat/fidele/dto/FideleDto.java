package com.eglise.secretariat.fidele.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FideleDto {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    private String sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String telephone;
    private String email;
    private String quartier;
    private String adresse;
    private String profession;
    private String statutMatrimonial; // Célibataire, Marié(e), Veuf/Veuve, etc.

    private boolean baptise;
    private LocalDate dateBapteme;
    private String lieuBapteme;

    private boolean actif = true;

    private List<EngagementDto> engagements = new ArrayList<>();

    public FideleDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStatutMatrimonial() {
        return statutMatrimonial;
    }

    public void setStatutMatrimonial(String statutMatrimonial) {
        this.statutMatrimonial = statutMatrimonial;
    }

    public boolean isBaptise() {
        return baptise;
    }

    public void setBaptise(boolean baptise) {
        this.baptise = baptise;
    }

    public LocalDate getDateBapteme() {
        return dateBapteme;
    }

    public void setDateBapteme(LocalDate dateBapteme) {
        this.dateBapteme = dateBapteme;
    }

    public String getLieuBapteme() {
        return lieuBapteme;
    }

    public void setLieuBapteme(String lieuBapteme) {
        this.lieuBapteme = lieuBapteme;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<EngagementDto> getEngagements() {
        return engagements;
    }

    public void setEngagements(List<EngagementDto> engagements) {
        this.engagements = engagements;
    }

}
