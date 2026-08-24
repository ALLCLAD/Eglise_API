package com.eglise.secretariat.mouvement;

import com.eglise.model.Fidele;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mouvement")
public class Mouvement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fidele_id", nullable = false)
    private Fidele fidele;

    @Column(name = "type_mouvement", nullable = false)
    private String typeMouvement; // "CARTE_MEMBRE" or "CARNET_DIME"

    @Column(name = "valeur")
    private Boolean valeur;

    @Column(name = "date_enregistrement", nullable = false)
    private LocalDateTime dateEnregistrement;

    public Mouvement() {}

    public Mouvement(Fidele fidele, String typeMouvement, Boolean valeur, LocalDateTime dateEnregistrement) {
        this.fidele = fidele;
        this.typeMouvement = typeMouvement;
        this.valeur = valeur;
        this.dateEnregistrement = dateEnregistrement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fidele getFidele() {
        return fidele;
    }

    public void setFidele(Fidele fidele) {
        this.fidele = fidele;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Boolean getValeur() {
        return valeur;
    }

    public void setValeur(Boolean valeur) {
        this.valeur = valeur;
    }

    public LocalDateTime getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(LocalDateTime dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }
}
