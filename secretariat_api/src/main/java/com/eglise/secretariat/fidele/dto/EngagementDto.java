package com.eglise.secretariat.fidele.dto;

import java.time.LocalDate;

public class EngagementDto {

    private Long id;
    private String nomEngagement;
    private String fonction;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean actif;

    public EngagementDto() {}

    public EngagementDto(Long id, String nomEngagement, String fonction, LocalDate dateDebut, LocalDate dateFin, boolean actif) {
        this.id = id;
        this.nomEngagement = nomEngagement;
        this.fonction = fonction;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.actif = actif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEngagement() {
        return nomEngagement;
    }

    public void setNomEngagement(String nomEngagement) {
        this.nomEngagement = nomEngagement;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

}
