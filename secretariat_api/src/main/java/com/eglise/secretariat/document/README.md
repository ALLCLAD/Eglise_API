# 📄 Module Génération de Documents PDF (`document`)

## 📌 Présentation du Module
Le module `document` s'occupe de la production automatique et de l'exportation des documents officiels de l'église au format PDF. Il combine des modèles de templates HTML (Thymeleaf) avec un moteur de rendu XHTML vers PDF (FlyingSaucer / OpenHTMLtoPDF).

---

## 🏗️ Architecture et Rôle des Fichiers

```
document/
├── DocumentController.java               # Contrôleur REST pour l'exportation PDF
├── FidelePdfExporter.java                # Générateur de la Fiche d'Inscription PDF
├── LetterExporter.java                  # Générateur de la Lettre de Recommandation PDF
└── dto/                                  # Objets de requête pour la création de documents
    └── LettreRecommandationRequestDto.java
```

---

## 📜 Description détaillée des Classes

### 1. `DocumentController.java`
* **Rôle** : Contrôleur REST exposant les téléchargements de documents PDF.
* **Endpoints exposés** :
  * `GET /api/documents/fidele/{id}/pdf` : Génère et retourne le flux binaire (PDF) de la **Fiche d'Inscription Individuelle** du fidèle.
  * `POST /api/documents/lettre-recommandation/pdf` : Génère le PDF de la **Lettre de Recommandation Sortante** (pour les fidèles voyageant ou transférés dans une autre église).

### 2. `FidelePdfExporter.java`
* **Rôle** : Service responsable de l'exportation de la fiche d'inscription.
* **Fonctionnement** :
  * Injection des données de l'entité `Fidele` dans le template Thymeleaf `fiche_inscription.html`.
  * Conversion du document HTML produit en document PDF standardisé A4 avec mise en page et en-têtes officiels.

### 3. `LetterExporter.java`
* **Rôle** : Service spécialisé dans l'exportation des lettres pastorales officielles.
* **Fonctionnement** :
  * Fusion des métadonnées contenues dans `LettreRecommandationRequestDto` avec le template Thymeleaf `lettre_recommandation.html`.
  * Production du fichier PDF prêt pour impression et signature pastorale.

---

## 📦 Data Transfer Objects (`dto/`)

* **`LettreRecommandationRequestDto.java`** : Encapsule les paramètres requis pour générer une lettre de recommandation (identifiant du fidèle, nom et adresse de l'église destinataire, motif du déplacement/transfert, nom du pasteur expéditeur).
