Medical Appointment SOAP - Documentation Complète
📋 Description du Projet
Système de gestion de rendez-vous médicaux utilisant une architecture SOAP (Services Web) avec :

Serveur JAX-WS : Service web SOAP pour la gestion des rendez-vous

Client SOAP : Application cliente pour consommer les services

Architecture : Client-Serveur avec services web

⚙️ Prérequis Techniques
Versions Requises
JDK : 1.8 (Java 8)

Maven : 3.6 ou supérieur

IDE : IntelliJ IDEA (recommandé) ou Eclipse

Vérification de l'installation
bash
java -version
# Output: java version "1.8.0_XX"

mvn -version
# Output: Apache Maven 3.6.3
🚀 Installation et Configuration
1. Cloner le projet
bash
git clone https://github.com/fatmabaccari/medical-appointment-soap.git
cd medical-appointment-soap
2. Configuration dans IntelliJ IDEA
Ouvrir le projet : File → Open → Sélectionner le dossier medical-appointment-soap

Configurer le JDK :

File → Project Structure → Project

Project SDK : Java 1.8

Project language level : 8

Importer comme projet Maven :

IntelliJ détecte automatiquement les pom.xml

Accepter l'import Maven

🏗️ Structure du Projet
text
medical-appointment-soap/
├── doc-time-server/          # Serveur SOAP
│   ├── src/main/java/
│   │   ├── server/ServeurJWS.java    # Point d'entrée serveur
│   │   ├── service/DocTime.java      # Interface du service
│   │   └── model/                    # Classes métier
│   └── pom.xml
├── doc-time-client/          # Client SOAP
│   ├── src/main/java/
│   │   ├── DocTime.java              # Client principal
│   │   └── org/sid/Main.java         # Point d'entrée client
│   └── pom.xml
└── README.md
🎯 Lancement du Projet
Étape 1 : Démarrer le Serveur

📌 Le serveur démarre sur : http://localhost:8080/

Étape 2 : Générer les classes clientes (si nécessaire)
bash
cd doc-time-client
mvn clean compile
Étape 3 : Lancer le Client


🔧 Commandes Maven Utiles
Pour le serveur :
bash
cd doc-time-server
mvn clean compile    # Nettoyer et compiler
mvn exec:java        # Exécuter le serveur
mvn package          # Créer le JAR
Pour le client :
bash
cd doc-time-client
mvn clean compile    # Nettoyer et compiler
mvn exec:java        # Exécuter le client
🌐 Services Web Disponibles
URL du Service WSDL
text
http://localhost:8080/service/docTime?wsdl




✅ Le projet est maintenant prêt à être utilisé ! Commencez par lancer le serveur, puis le client.

