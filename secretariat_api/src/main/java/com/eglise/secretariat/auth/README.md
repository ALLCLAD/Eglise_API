# 🔐 Module Authentification (`auth`)

## 📌 Présentation du Module
Le module `auth` gère la sécurité des accès au secrétariat, l'authentification des utilisateurs/administrateurs, la vérification des crédentiels et la gestion des comptes de profil.

---

## 🏗️ Architecture et Rôle des Fichiers

```
auth/
├── AuthController.java   # Contrôleur REST (Exposition des Endpoints HTTP)
├── AuthService.java      # Service Métier (Logique d'authentification et gestion JWT)
└── dto/                  # Data Transfer Objects (Requêtes et Réponses JSON)
    ├── LoginRequestDto.java
    ├── AuthResponseDto.java
    └── UpdateProfileDto.java
```

---

## 📜 Description détaillée des Classes

### 1. `AuthController.java`
* **Rôle** : Contrôleur Spring REST gérant les requêtes HTTP d'authentification.
* **Endpoints exposés** :
  * `POST /api/auth/login` : Permet la connexion en vérifiant le nom d'utilisateur/email et mot de passe. Retourne un jeton JWT en cas de succès.
  * `PUT /api/auth/update-profile` : Permet à un utilisateur connecté de modifier ses informations de profil (nom, email, mot de passe).

### 2. `AuthService.java`
* **Rôle** : Service contenant la logique métier.
* **Responsabilités** :
  * Validation des crédentiels reçus via `LoginRequestDto`.
  * Interaction avec la couche de persistance ou le fournisseur de sécurité pour charger l'utilisateur.
  * Génération du jeton JWT d'accès en s'appuyant sur `JwtUtil` (`shared/security`).
  * Mise à jour des informations de compte utilisateur.

---

## 📦 Data Transfer Objects (`dto/`)

* **`LoginRequestDto.java`** : Représente le corps de la requête HTTP pour la tentative de connexion (`username` / `password`).
* **`AuthResponseDto.java`** : Contient le jeton d'accès JWT généré, son type (`Bearer`), ainsi que les métadonnées essentielles de l'utilisateur connecté.
* **`UpdateProfileDto.java`** : Données transmises lors d'une demande de modification de profil (nouveau nom, nouvel email, nouveau mot de passe).
