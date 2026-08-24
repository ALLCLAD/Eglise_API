package com.eglise.secretariat.mouvement.dto;

import java.time.LocalDateTime;

public class ConformiteStatusDto {
    private Long fideleId;
    private Boolean carteMembreValide;
    private Boolean carnetDimeValide;
    private Boolean estEnRegle;
    private LocalDateTime dateEnregistrement;

    public ConformiteStatusDto() {}

    public ConformiteStatusDto(Long fideleId, Boolean carteMembreValide, Boolean carnetDimeValide, Boolean estEnRegle, LocalDateTime dateEnregistrement) {
        this.fideleId = fideleId;
        this.carteMembreValide = carteMembreValide;
        this.carnetDimeValide = carnetDimeValide;
        this.estEnRegle = estEnRegle;
        this.dateEnregistrement = dateEnregistrement;
    }

    public Long getFideleId() {
        return fideleId;
    }

    public void setFideleId(Long fideleId) {
        this.fideleId = fideleId;
    }

    public Boolean getCarteMembreValide() {
        return carteMembreValide;
    }

    public void setCarteMembreValide(Boolean carteMembreValide) {
        this.carteMembreValide = carteMembreValide;
    }

    public Boolean getCarnetDimeValide() {
        return carnetDimeValide;
    }

    public void setCarnetDimeValide(Boolean carnetDimeValide) {
        this.carnetDimeValide = carnetDimeValide;
    }

    public Boolean getEstEnRegle() {
        return estEnRegle;
    }

    public void setEstEnRegle(Boolean estEnRegle) {
        this.estEnRegle = estEnRegle;
    }

    public LocalDateTime getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(LocalDateTime dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }
}
