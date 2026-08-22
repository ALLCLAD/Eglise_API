package com.eglise.secretariat.document;

import com.eglise.model.*;
import com.eglise.model.enumeration.Sexe;
import com.eglise.model.enumeration.Statut;
import com.eglise.secretariat.document.dto.LettreRecommandationRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.File;
import java.time.LocalDate;

@SpringBootTest
@org.springframework.test.context.ActiveProfiles("test")
public class PdfGenerationTest {

    @Autowired
    private FidelePdfExporter fidelePdfExporter;

    @Autowired
    private LetterExporter letterExporter;

    @Test
    public void generateSamplePdfs() throws Exception {
        // 1. Données fictives pour le test
        Fidele fidele = new Fidele();
        fidele.setId(1L);
        fidele.setNom("KOFFI");
        fidele.setPrenoms("Jean-Baptiste");
        fidele.setDateNaissance(LocalDate.of(1995, 5, 12));
        fidele.setLieuNaissance("Lomé");
        fidele.setEthnie("Ewé");
        fidele.setSexe(Sexe.MASCULIN);
        fidele.setProfession("Informaticien");

        // Adresse
        AdresseCoordonnees adresse = new AdresseCoordonnees();
        adresse.setDomicile("Hedzranawoé");
        adresse.setMaison("Maison Koffi");
        fidele.setAdresse(adresse);

        // Filiation
        Filiation filiation = new Filiation();
        filiation.setNomPere("KOFFI");
        filiation.setPrenomPere("Paul");
        filiation.setNomMere("DOSSEH");
        filiation.setPrenomMere("Marie");
        fidele.setFiliation(filiation);

        // Situation Matrimoniale
        SituationMatrimoniale mat = new SituationMatrimoniale();
        mat.setStatutActuel(Statut.MARIE);
        mat.setDateMariageEglise(LocalDate.of(2021, 8, 20));
        mat.setEgliseMariage("AD Adidogomé");
        mat.setPasteurCelebrant("Pasteur KOUVI");
        mat.setNombreGarçons(2);
        mat.setNombreFilles(1);
        fidele.setSituationMatrimoniale(mat);

        // Statut et Parcours Spirituel
        StatutFidele statut = new StatutFidele();
        statut.setEstMembreActifDepuis(LocalDate.of(2018, 1, 15));
        statut.setEstSousDiscipline(false);
        fidele.setStatutFidele(statut);

        ParcoursSpirituel parcours = new ParcoursSpirituel();
        parcours.setDateBaptemeEau(LocalDate.of(2016, 4, 10));
        parcours.setDateBaptemeSaintEsprit(LocalDate.of(2017, 6, 4));
        fidele.setParcoursSpirituel(parcours);

        // DTO Lettre de Recommandation
        LettreRecommandationRequestDto request = new LettreRecommandationRequestDto(
                1L,
                "Transfert pour raison professionnelle",
                "Temple de Kpalimé",
                LocalDate.now()
        );

        // 2. Génération du PDF de la Fiche d'Inscription
        byte[] fichePdfBytes = fidelePdfExporter.generateFidelePdf(fidele);
        File ficheFile = new File("test_fiche_inscription.pdf");
        try (FileOutputStream fos = new FileOutputStream(ficheFile)) {
            fos.write(fichePdfBytes);
        }
        System.out.println("Fiche PDF générée : " + ficheFile.getAbsolutePath());

        // 3. Génération du PDF de la Lettre de Recommandation
        byte[] lettrePdfBytes = letterExporter.generateLettreRecommandationPdf(fidele, request);
        File lettreFile = new File("test_lettre_recommandation.pdf");
        try (FileOutputStream fos = new FileOutputStream(lettreFile)) {
            fos.write(lettrePdfBytes);
        }
        System.out.println("Lettre PDF générée : " + lettreFile.getAbsolutePath());
    }
}
