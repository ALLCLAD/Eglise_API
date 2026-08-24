package com.eglise.secretariat.dashboard.dto;

import java.util.Map;

public class DashboardStatsDto {
    private long totalInscrits;
    private Map<String, Long> repartitionParQuartier;
    private double tauxMembresAJourCotisationDime;
    private Map<String, Long> fluxMensuels;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long totalInscrits, Map<String, Long> repartitionParQuartier, double tauxMembresAJourCotisationDime, Map<String, Long> fluxMensuels) {
        this.totalInscrits = totalInscrits;
        this.repartitionParQuartier = repartitionParQuartier;
        this.tauxMembresAJourCotisationDime = tauxMembresAJourCotisationDime;
        this.fluxMensuels = fluxMensuels;
    }

    public long getTotalInscrits() {
        return totalInscrits;
    }

    public void setTotalInscrits(long totalInscrits) {
        this.totalInscrits = totalInscrits;
    }

    public Map<String, Long> getRepartitionParQuartier() {
        return repartitionParQuartier;
    }

    public void setRepartitionParQuartier(Map<String, Long> repartitionParQuartier) {
        this.repartitionParQuartier = repartitionParQuartier;
    }

    public double getTauxMembresAJourCotisationDime() {
        return tauxMembresAJourCotisationDime;
    }

    public void setTauxMembresAJourCotisationDime(double tauxMembresAJourCotisationDime) {
        this.tauxMembresAJourCotisationDime = tauxMembresAJourCotisationDime;
    }

    public Map<String, Long> getFluxMensuels() {
        return fluxMensuels;
    }

    public void setFluxMensuels(Map<String, Long> fluxMensuels) {
        this.fluxMensuels = fluxMensuels;
    }
}
