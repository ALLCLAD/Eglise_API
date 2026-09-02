# 🛠️ Module Transverse (`shared`)

## 📌 Présentation du Module
Le module `shared` contient l'ensemble des composants transverses réutilisables par les autres modules du projet : la configuration de la sécurité Spring Security (JWT), la documentation OpenAPI/Swagger UI, les filtres CORS, les beans globaux et la gestion centralisée des erreurs et exceptions.

---

## 🏗️ Architecture et Rôle des Fichiers

```
shared/
├── config/                     # Configuration de l'application
│   ├── AppBeans.java           # Déclaration de beans Spring génériques
│   ├── CorsConfig.java         # Configuration du partage d'ressources (CORS)
│   ├── OpenApiConfig.java      # Configuration Swagger / OpenAPI 3
│   └── SecurityConfig.java     # Chaîne de filtres de sécurité Spring Security
├── exception/                  # Handling global des erreurs HTTP
│   ├── ApiError.java           # Format JSON standardisé des réponses d'erreur
│   ├── GlobalExceptionHandler.java # Intercepteur d'exceptions global (@ControllerAdvice)
│   └── ResourceNotFoundException.java # Exception personnalisée pour ressource introuvable
└── security/                   # Composants JWT
    ├── JwtFilter.java          # Filtre HTTP d'extraction et validation du Bearer Token
    └── JwtUtil.java            # Service utilitaire de création et décodage de jetons JWT
```

---

## 📜 Description détaillée des Packages et Classes

### 📂 1. `config/` (Configurations Spring Boot)
* **`SecurityConfig.java`** : Configure la chaîne de filtres Spring Security (`SecurityFilterChain`). Définit la politique de session stateless, active les règles d'autorisation sur les URL, enregistre `JwtFilter` et gère le chiffrement des mots de passe (`PasswordEncoder`).
* **`OpenApiConfig.java`** : Définit la configuration d'OpenAPI 3 / Swagger UI avec le schéma d'authentification `SecurityScheme` de type `Bearer JWT`.
* **`CorsConfig.java`** : Autorise les requêtes HTTP multi-origines (React, Vue, applications mobiles) en définissant les origines, en-têtes et méthodes HTTP permis.
* **`AppBeans.java`** : Contient la déclaration de beans réutilisables (ex: `RestTemplate`, `ObjectMapper`, etc.).

### 📂 2. `security/` (Gestion de la Sécurité JWT)
* **`JwtUtil.java`** : Utilitaire pour la création, le signe avec clé secrète, le décodage et la vérification d'expiration des jetons JWT.
* **`JwtFilter.java`** : Filtre de requête HTTP (étendant `OncePerRequestFilter`). Il extrait l'en-tête `Authorization: Bearer <token>`, valide le jeton et authentifie le contexte Spring Security (`SecurityContextHolder`).

### 📂 3. `exception/` (Gestionnaire d'Exceptions Global)
* **`GlobalExceptionHandler.java`** : Classe annotée `@ControllerAdvice` qui intercepte les exceptions levées dans l'application (ex: `ResourceNotFoundException`, erreurs de validation DTO, exceptions de sécurité) pour retourner des réponses d'erreur JSON claires et cohérentes.
* **`ApiError.java`** : Structure DTO standardisée décrivant une erreur HTTP (`timestamp`, `status`, `error`, `message`, `path`).
* **`ResourceNotFoundException.java`** : Exception standard levée lorsqu'une entité ou ressource demandée n'est pas présente en base de données.
