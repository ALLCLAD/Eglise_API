# 📊 Module Statistiques & Tableau de Bord (`dashboard`)

## 📌 Présentation du Module
Le module `dashboard` regroupe et consolide les données analytiques de l'église. Il fournit aux responsables et administrateurs des métriques en temps réel sur la répartition des fidèles, l'assiduité aux dîmes, la croissance démographique et la répartition géographique.

---

## 🏗️ Architecture et Rôle des Fichiers

```
dashboard/
├── DashboardController.java    # Contrôleur REST du tableau de bord
├── DashboardService.java       # Service de calcul des statistiques
└── dto/                        # Données consolidées de synthèse
    └── DashboardStatsDto.java
```

---

## 📜 Description détaillée des Classes

### 1. `DashboardController.java`
* **Rôle** : Contrôleur REST exposant l'API du tableau de bord.
* **Endpoints exposés** :
  * `GET /api/dashboard/stats` : Retourne la totalité des métriques et indicateurs clés de performance du secrétariat.

### 2. `DashboardService.java`
* **Rôle** : Moteur d'agrégation et de calcul des données analytiques.
* **Responsabilités** :
  * Interrogation des bases de données pour calculer le nombre total de fidèles actifs.
  * Répartition des membres par tranche d'âge, par sexe et par quartier/zone.
  * Calcul du pourcentage de régularité du paiement des dîmes.
  * Analyse des flux d'entrées (nouveaux baptisés, arrivants OCR) et de sorties sur les derniers mois.

---

## 📦 Data Transfer Objects (`dto/`)

* **`DashboardStatsDto.java`** : Objet de réponse global regroupant toutes les métriques de l'application (ex: `totalFideles`, `repartitionParQuartier`, `repartitionParSexe`, `tauxRegulariteDime`, `fluxMensuels`).
