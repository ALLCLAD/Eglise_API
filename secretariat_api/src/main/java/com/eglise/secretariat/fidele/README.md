# 👥 Module Gestion des Fidèles (`fidele`)

## 📌 Présentation du Module
Le module `fidele` constitue le cœur de la gestion des membres de l'église. Il gère la création des fiches d'inscription, le filtrage dynamique multi-critères, le suivi des engagements, les cartes de membre et les carnets de dîme.

---

## 🏗️ Architecture et Rôle des Fichiers

```
fidele/
├── FideleController.java    # Contrôleur REST (API pour la gestion des fidèles)
├── FideleService.java       # Service Métier (Logique d'inscription & mise à jour)
├── FideleRepository.java    # Interface de persistance JPA
├── FideleSpecification.java # Spécifications JPA pour filtrage dynamique
└── dto/                     # Objets de transfert de données
    ├── FideleDto.java
    └── EngagementDto.java
```

---

## 📜 Description détaillée des Classes

### 1. `FideleController.java`
* **Rôle** : Contrôleur REST exposant la gestion des fidèles.
* **Endpoints exposés** :
  * `GET /api/fideles` : Liste paginée des fidèles avec support de filtres dynamiques (nom, prénom, quartier, statut de membre, etc.).
  * `GET /api/fideles/{id}` : Récupération des détails complets d'un fidèle.
  * `POST /api/fideles` : Inscription d'un nouveau fidèle.
  * `PUT /api/fideles/{id}` : Mise à jour des informations d'un fidèle existant.
  * `DELETE /api/fideles/{id}` : Archiving ou suppression d'un membre.

### 2. `FideleService.java`
* **Rôle** : Logique métier liée aux membres de l'église.
* **Responsabilités** :
  * Validation des enums métier (statuts, fréquence de dîme, tranche d'âge, sexe).
  * Conversion bidirectionnelle entre l'entité JPA `Fidele` (importée depuis `Package-Mapping`) et les DTOs `FideleDto`.
  * Gestion des liens d'engagement ecclésiastique (baptême, mariage, activités/mouvements).

### 3. `FideleRepository.java`
* **Rôle** : Interface Spring Data JPA pour l'entité `Fidele`.
* **Particularité** : Étend `JpaRepository<Fidele, Long>` et `JpaSpecificationExecutor<Fidele>` pour autoriser les requêtes dynamiques basées sur Criteria API.

### 4. `FideleSpecification.java`
* **Rôle** : Fournisseur de critères de recherche dynamiques JPA (`Specification<Fidele>`).
* **Fonctionnalités** : Permet de construire à la volée des requêtes SQL flexibles combinant plusieurs critères (ex: filtrer par quartier, groupe d'âge, possession d'une carte de membre ou fréquence de dîme).

---

## 📦 Data Transfer Objects (`dto/`)

* **`FideleDto.java`** : Représente la structure complète des données transmises à l'API pour un fidèle (état civil, contact, quartier, statut d'adhésion, informations spirituelles).
* **`EngagementDto.java`** : Encapsule les détails sur l'engagement spirituel et ministériel d'un membre au sein de l'assemblée.
