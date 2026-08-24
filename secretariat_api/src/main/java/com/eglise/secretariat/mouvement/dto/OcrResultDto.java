package com.eglise.secretariat.mouvement.dto;

import java.time.LocalDate;

public class OcrResultDto {
    private String pasteurSignataire;
    private String egliseOrigine;
    private LocalDate datePresentation;
    private String rawText;

    public OcrResultDto() {}

    public OcrResultDto(String pasteurSignataire, String egliseOrigine, LocalDate datePresentation, String rawText) {
        this.pasteurSignataire = pasteurSignataire;
        this.egliseOrigine = egliseOrigine;
        this.datePresentation = datePresentation;
        this.rawText = rawText;
    }

    public String getPasteurSignataire() {
        return pasteurSignataire;
    }

    public void setPasteurSignataire(String pasteurSignataire) {
        this.pasteurSignataire = pasteurSignataire;
    }

    public String getEgliseOrigine() {
        return egliseOrigine;
    }

    public void setEgliseOrigine(String egliseOrigine) {
        this.egliseOrigine = egliseOrigine;
    }

    public LocalDate getDatePresentation() {
        return datePresentation;
    }

    public void setDatePresentation(LocalDate datePresentation) {
        this.datePresentation = datePresentation;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
