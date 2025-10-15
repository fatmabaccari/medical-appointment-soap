# Medical Appointment SOAP 📋 MedTime

[![Java](https://img.shields.io/badge/Java-1.8-blue)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-brightgreen)](https://maven.apache.org/)
[![Status](https://img.shields.io/badge/Status-Production-green)]()

## Description du Projet
**Medical Appointment SOAP** est un système de gestion de rendez-vous médicaux basé sur une architecture **SOAP (Services Web)**.  

Le projet inclut :  

- **Serveur JAX-WS** : Service Web SOAP pour la gestion des rendez-vous.  
- **Client SOAP** : Application cliente consommant les services exposés.  
- **Architecture** : Modèle Client-Serveur via des services Web SOAP.  

---

## ⚙️ Prérequis Techniques

| Technologie | Version Requise |
|------------|----------------|
| **JDK** | 1.8 (Java 8) |
| **Maven** | 3.6 ou supérieur |
| **IDE** | IntelliJ IDEA (recommandé) ou Eclipse |

### Vérification de l'installation

```bash
java -version
# Output: java version "1.8.0_XX"

mvn -version
```
# Output: Apache Maven 3.6.3

🚀 Installation et Configuration
1️⃣ Cloner le projet
```bash
git clone https://github.com/fatmabaccari/medical-appointment-soap.git
cd medical-appointment-soap
```
Configurer le JDK :
File → Project Structure → Project → Project SDK : Java 1.8
Project language level : 8
🎯 Lancement du Projet
Étape 1 : Démarrer le serveur
```bash
cd doc-time-server
mvn clean compile exec:java
```


📌 Le serveur sera accessible sur : 

http://localhost:8084/docTime
📌 Le WSDL du service est disponible ici :

http://localhost:8084/docTime?wsdl

Étape 2 : Générer les classes clientes 
```bash
cd doc-time-client
mvn clean compile
```
Étape 3 : Lancer le client
```bash
mvn exec:java
```
