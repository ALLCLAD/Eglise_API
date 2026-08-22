package com.eglise.secretariat.fidele.dto;

import com.eglise.model.enumeration.FrequenceDime;
import com.eglise.model.enumeration.Sexe;
import com.eglise.model.enumeration.Statut;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FideleDto {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenoms;

    private Sexe sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String ethnie;
    private String profession;
    private String niveauEtude;

    // Contact & Adresse
    private String telephone;
    private String contactMoov;
    private String email;
    private String quartier;
    private String adresse;
    private String prefectureRegion;

    // Situation matrimoniale & Famille
    private Statut statutMatrimonial; // CELIBATAIRE, MARIE, etc.
    private LocalDate dateMariage;
    private String egliseMariage;
    private String pasteurMariage;
    private String nomConjoint;
    private String confessionFoiConjoint;
    private Integer nombreGarcons;
    private Integer nombreFilles;

    // Filiation
    private String nomPere;
    private String prenomPere;
    private String nomMere;
    private String prenomMere;

    // Parcours spirituel & Denominations
    private LocalDate dateConversion;
    private String egliseConversion;
    private boolean baptise;
    private LocalDate dateBapteme;
    private String lieuBapteme;
    private String pasteurBapteme;
    private LocalDate dateBaptemeEsprit;
    private String lieuBaptemeEsprit;
    private String ancienneDenomination;
    private String nouvelleDenomination;

    // Lettre entrante & Intégration
    private Boolean lettreRecommandationPresentee;
    private LocalDate dateLettreRecommandation;
    private String pasteurLettreRecommandation;
    private String egliseLettreRecommandation;
    private LocalDate dateIntegrationAdidogome;

    // Vie de membre & Dîme
    private Boolean carteMembreValide;
    private Boolean regulierReunions;
    private Boolean carnetDimeValide;
    private Boolean payeDimes;
    private FrequenceDime frequenceDime; // REGULIEREMENT, RAREMENT...

    private boolean actif = true;
    private List<EngagementDto> engagements = new ArrayList<>();

    public FideleDto() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenoms() { return prenoms; }
    public void setPrenoms(String prenoms) { this.prenoms = prenoms; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public String getEthnie() { return ethnie; }
    public void setEthnie(String ethnie) { this.ethnie = ethnie; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getNiveauEtude() { return niveauEtude; }
    public void setNiveauEtude(String niveauEtude) { this.niveauEtude = niveauEtude; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getContactMoov() { return contactMoov; }
    public void setContactMoov(String contactMoov) { this.contactMoov = contactMoov; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getPrefectureRegion() { return prefectureRegion; }
    public void setPrefectureRegion(String prefectureRegion) { this.prefectureRegion = prefectureRegion; }

    public Statut getStatutMatrimonial() { return statutMatrimonial; }
    public void setStatutMatrimonial(Statut statutMatrimonial) { this.statutMatrimonial = statutMatrimonial; }

    public LocalDate getDateMariage() { return dateMariage; }
    public void setDateMariage(LocalDate dateMariage) { this.dateMariage = dateMariage; }

    public String getEgliseMariage() { return egliseMariage; }
    public void setEgliseMariage(String egliseMariage) { this.egliseMariage = egliseMariage; }

    public String getPasteurMariage() { return pasteurMariage; }
    public void setPasteurMariage(String pasteurMariage) { this.pasteurMariage = pasteurMariage; }

    public String getNomConjoint() { return nomConjoint; }
    public void setNomConjoint(String nomConjoint) { this.nomConjoint = nomConjoint; }

    public String getConfessionFoiConjoint() { return confessionFoiConjoint; }
    public void setConfessionFoiConjoint(String confessionFoiConjoint) { this.confessionFoiConjoint = confessionFoiConjoint; }

    public Integer getNombreGarcons() { return nombreGarcons; }
    public void setNombreGarcons(Integer nombreGarcons) { this.nombreGarcons = nombreGarcons; }

    public Integer getNombreFilles() { return nombreFilles; }
    public void setNombreFilles(Integer nombreFilles) { this.nombreFilles = nombreFilles; }

    public String getNomPere() { return nomPere; }
    public void setNomPere(String nomPere) { this.nomPere = nomPere; }

    public String getPrenomPere() { return prenomPere; }
    public void setPrenomPere(String prenomPere) { this.prenomPere = prenomPere; }

    public String getNomMere() { return nomMere; }
    public void setNomMere(String nomMere) { this.nomMere = nomMere; }

    public String getPrenomMere() { return prenomMere; }
    public void setPrenomMere(String prenomMere) { this.prenomMere = prenomMere; }

    public LocalDate getDateConversion() { return dateConversion; }
    public void setDateConversion(LocalDate dateConversion) { this.dateConversion = dateConversion; }

    public String getEgliseConversion() { return egliseConversion; }
    public void setEgliseConversion(String egliseConversion) { this.egliseConversion = egliseConversion; }

    public boolean isBaptise() { return baptise; }
    public void setBaptise(boolean baptise) { this.baptise = baptise; }

    public LocalDate getDateBapteme() { return dateBapteme; }
    public void setDateBapteme(LocalDate dateBapteme) { this.dateBapteme = dateBapteme; }

    public String getLieuBapteme() { return lieuBapteme; }
    public void setLieuBapteme(String lieuBapteme) { this.lieuBapteme = lieuBapteme; }

    public String getPasteurBapteme() { return pasteurBapteme; }
    public void setPasteurBapteme(String pasteurBapteme) { this.pasteurBapteme = pasteurBapteme; }

    public LocalDate getDateBaptemeEsprit() { return dateBaptemeEsprit; }
    public void setDateBaptemeEsprit(LocalDate dateBaptemeEsprit) { this.dateBaptemeEsprit = dateBaptemeEsprit; }

    public String getLieuBaptemeEsprit() { return lieuBaptemeEsprit; }
    public void setLieuBaptemeEsprit(String lieuBaptemeEsprit) { this.lieuBaptemeEsprit = lieuBaptemeEsprit; }

    public String getAncienneDenomination() { return ancienneDenomination; }
    public void setAncienneDenomination(String ancienneDenomination) { this.ancienneDenomination = ancienneDenomination; }

    public String getNouvelleDenomination() { return nouvelleDenomination; }
    public void setNouvelleDenomination(String nouvelleDenomination) { this.nouvelleDenomination = nouvelleDenomination; }

    public Boolean getLettreRecommandationPresentee() { return lettreRecommandationPresentee; }
    public void setLettreRecommandationPresentee(Boolean lettreRecommandationPresentee) { this.lettreRecommandationPresentee = lettreRecommandationPresentee; }

    public LocalDate getDateLettreRecommandation() { return dateLettreRecommandation; }
    public void setDateLettreRecommandation(LocalDate dateLettreRecommandation) { this.dateLettreRecommandation = dateLettreRecommandation; }

    public String getPasteurLettreRecommandation() { return pasteurLettreRecommandation; }
    public void setPasteurLettreRecommandation(String pasteurLettreRecommandation) { this.pasteurLettreRecommandation = pasteurLettreRecommandation; }

    public String getEgliseLettreRecommandation() { return egliseLettreRecommandation; }
    public void setEgliseLettreRecommandation(String egliseLettreRecommandation) { this.egliseLettreRecommandation = egliseLettreRecommandation; }

    public LocalDate getDateIntegrationAdidogome() { return dateIntegrationAdidogome; }
    public void setDateIntegrationAdidogome(LocalDate dateIntegrationAdidogome) { this.dateIntegrationAdidogome = dateIntegrationAdidogome; }

    public Boolean getCarteMembreValide() { return carteMembreValide; }
    public void setCarteMembreValide(Boolean carteMembreValide) { this.carteMembreValide = carteMembreValide; }

    public Boolean getRegulierReunions() { return regulierReunions; }
    public void setRegulierReunions(Boolean regulierReunions) { this.regulierReunions = regulierReunions; }

    public Boolean getCarnetDimeValide() { return carnetDimeValide; }
    public void setCarnetDimeValide(Boolean carnetDimeValide) { this.carnetDimeValide = carnetDimeValide; }

    public Boolean getPayeDimes() { return payeDimes; }
    public void setPayeDimes(Boolean payeDimes) { this.payeDimes = payeDimes; }

    public FrequenceDime getFrequenceDime() { return frequenceDime; }
    public void setFrequenceDime(FrequenceDime frequenceDime) { this.frequenceDime = frequenceDime; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public List<EngagementDto> getEngagements() { return engagements; }
    public void setEngagements(List<EngagementDto> engagements) { this.engagements = engagements; }
}