# ⛪ API Secrétariat Église (Spring Boot)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14%2B-blue.svg)](https://www.postgresql.org/)
[![Package-Mapping](https://img.shields.io/badge/Package--Mapping-v1.0.0-blueviolet.svg)](https://github.com/ALLCLAD/Package-Mapping)

Bienvenue sur le projet **API Secrétariat Église**. Cette application backend développée sous **Spring Boot 3.3** et **Java 21** gère la logique métier du secrétariat d'église et s'appuie sur la librairie partagée [`Package-Mapping`](https://github.com/ALLCLAD/Package-Mapping) pour le modèle de données JPA.

---

## 🛠️ Prérequis Logiciels & Installation rapide

### 1. Outils de base nécessaires
* **Git** ([Télécharger Git](https://git-scm.com/))
* **PostgreSQL** (version 14+) + **pgAdmin** ou **DBeaver** ([Télécharger PostgreSQL](https://www.postgresql.org/download/))
* **Java JDK 21** ([Télécharger Eclipse Temurin JDK 21](https://adoptium.net/))

> 💡 **Remarque importante pour l'équipe (Maven) :**  
> **Vous n'avez PAS besoin d'installer Maven séparément sur votre machine !** Le projet inclut le **Maven Wrapper** (`mvnw` sous Linux/Mac et `mvnw.cmd` sous Windows). Il téléchargera et utilisera automatiquement la bonne version de Maven.

---

### 💡 (Optionnel) Installation ultra-rapide sous Windows via Scoop

Si vous êtes sous Windows, vous pouvez tout installer en une seule ligne de commande PowerShell grâce au gestionnaire de paquets **Scoop** :

1. **Installer Scoop (si non installé) :**
   ```powershell
   Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
   Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression
   ```

2. **Installer Java 21, Git et Maven en une commande :**
   ```powershell
   scoop bucket add java
   scoop install openjdk21 git maven
   ```

---

## 🚀 Installation & Démarrage Rapide

### 1. Cloner le dépôt Git

```bash
git clone https://github.com/ALLCLAD/secretariat_api.git
cd secretariat_api
```

---

### 2. Créer la Base de Données PostgreSQL

Ouvrez pgAdmin, DBeaver ou votre terminal PostgreSQL (`psql`) et créez une base de données nommée `secretariat_db` :

```sql
CREATE DATABASE secretariat_db;
```

---

### 3. Configurer les Variables d'Environnement (`.env`)

Pour garantir la sécurité des accès à la base de données, les identifiants locaux ne sont pas écrits en dur dans le code source mais gérés via un fichier `.env`.

1. À la racine du projet, créez un fichier nommé **`.env`** (au même niveau que le `pom.xml`).
2. Copiez-y la structure ci-dessous en adaptant votre mot de passe PostgreSQL local :

```env
# Configuration de la Base de Données PostgreSQL locale
DB_URL=jdbc:postgresql://localhost:5432/secretariat_db
DB_USERNAME=postgres
DB_PASSWORD=votre_mot_de_passe_local
```

> ⚠️ **IMPORTANT :** Le fichier `.env` est automatiquement ignoré par Git (`.gitignore`). **Ne le commitez jamais sur GitHub.**

---

### 4. Configuration dans l'IDE

#### 🔹 Sous IntelliJ IDEA (Recommandé)
1. Ouvrez IntelliJ ➔ **File** ➔ **Open...** ➔ Sélectionnez le dossier `secretariat_api`.
2. L'IDE va automatiquement télécharger les dépendances Maven (y compris la librairie distante `Package-Mapping` depuis JitPack).
3. Si le téléchargement ne se lance pas automatiquement :
   - Faites un clic droit sur `pom.xml` ➔ **Maven** ➔ **Reload Project**.
4. Vérifiez le SDK du projet :
   - **File** ➔ **Project Structure** ➔ **Project** ➔ Sélectionnez **SDK 21**.
5. Exécutez le projet en lançant la classe `com.eglise.secretariat.SecretariatApiApplication`.

#### 🔹 Sous VS Code
1. Installez l'extension **Extension Pack for Java** (Microsoft).
2. Ouvrez le dossier dans VS Code (`File` ➔ `Open Folder`).
3. Attendez la fin de l'indexation de l'espace de travail Java.
4. Si les dépendances ou les entités ne sont pas reconnues :
   - Palette de commandes (`Ctrl + Shift + P`) ➔ **`Java: Clean Java Language Server Workspace`** ➔ **Restart and delete**.
5. Lancez l'application via le bouton **Run** au-dessus de la méthode `main` dans `SecretariatApiApplication.java`.

---

### 5. Lancement en Ligne de Commande (Terminal)

Vous pouvez également compiler et lancer l'application avec Maven Wrapper :

```bash
# Compilation et téléchargement des dépendances
./mvnw clean compile

# Démarrage du serveur Spring Boot
./mvnw spring-boot:run
```

L'API démarrera par défaut sur l'URL : `http://localhost:8081`

---

## 📦 Architecture & Librairie Partagée

L'application consomme la librairie externe **Package-Mapping** publiée sur JitPack (`com.github.ALLCLAD:Package-Mapping:v1.0.0`).

- **Modèle de Données JPA (Librairie)** : `com.eglise.model.*` (`Fidele`, `EngagementMinistere`, `LettreDeRecommandation`, etc.)
- **Scan des Entités** : Activé dans `SecretariatApiApplication` via `@EntityScan(basePackages = {"com.eglise.secretariat", "com.eglise.model"})`
- **Application Backend** : `com.eglise.secretariat.*` (Repositories, Services, Controllers REST)

---

## ❓ Résolution des Problèmes Fréquents

| Symptôme / Erreur | Cause probable | Solution |
| :--- | :--- | :--- |
| `FATAL: authentification par mot de passe échouée` | Mot de passe PostgreSQL incorrect | Vérifiez la valeur de `DB_PASSWORD` dans votre fichier `.env`. |
| `Port 8081 already in use` | Le port est occupé par une autre instance | Modifiez `server.port=8082` dans `src/main/resources/application.properties`. |
| `Package com.eglise.model does not exist` | La librairie JitPack n'a pas été téléchargée | Exécutez `mvn clean compile` dans le terminal et rechargez Maven dans l'IDE. |
