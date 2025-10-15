import com.raps.code.generate.ws.Medecin;
import com.raps.code.generate.ws.DocTime_Service;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class DocTime extends Application {

    private com.raps.code.generate.ws.DocTime DocTime;
    private TextArea doctorListArea;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            // Crée une instance du service DocTime
            DocTime_Service docTimeService = new DocTime_Service();
            // OBTENIR LE PORT - CETTE LIGNE MANQUE DANS VOTRE CODE
            DocTime = docTimeService.getDocTimePort();

            System.out.println("Service web initialisé avec succès");
        } catch (Exception e) {
            System.err.println("Erreur de connexion au service web: " + e.getMessage());
            e.printStackTrace(); // Ajouter le stack trace pour plus de détails
            showErrorDialog("Le service web n'est pas accessible. Vérifiez qu'il est démarré.");
        }



        // ScrollPane principal pour permettre le défilement
        ScrollPane mainScrollPane = new ScrollPane();
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background: #f8f9fa; -fx-border-color: transparent;");
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Layout principal
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f8f9fa;");

        // Titre principal
        Label title = new Label("MedTime");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // === SECTION RECHERCHE MÉDECINS ===
        VBox searchSection = createStyledSection("Recherche de Médecins par Spécialité");

        Label specialiteLabel = new Label("Choisissez une spécialité :");
        specialiteLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Grille de boutons pour les spécialités
        GridPane specialitesGrid = new GridPane();
        specialitesGrid.setHgap(10);
        specialitesGrid.setVgap(10);
        specialitesGrid.setPadding(new Insets(10));

        // Liste des spécialités disponibles
        String[] specialites = {
                "Cardiologie", "Dermatologie", "Pédiatrie", "Gynécologie",
                "Ophtalmologie", "Orthopédie", "Neurologie", "Psychiatrie",
                "Radiologie", "Chirurgie", "Médecine Générale", "Urgences"
        };

        // Création des boutons pour chaque spécialité
        for (int i = 0; i < specialites.length; i++) {
            String specialite = specialites[i];
            Button specButton = new Button(specialite);
            styleSpecialtyButton(specButton);

            specButton.setOnAction(e -> {
                try {
                    if (DocTime != null) {
                        List<Medecin> medecins = DocTime.getMedecinsParSpecialite(specialite);
                        doctorListArea.clear();
                        if (medecins.isEmpty()) {
                            doctorListArea.setText("Aucun médecin trouvé pour la spécialité : " + specialite);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Médecins en ").append(specialite).append(" :\n\n");
                            for (Medecin medecin : medecins) {
                                sb.append("• ").append(medecin.getNom()).append("\n");
                                if (medecin.getSpecialite() != null && !medecin.getSpecialite().equals(specialite)) {
                                    sb.append("  (").append(medecin.getSpecialite()).append(")\n");
                                }
                                sb.append("\n");
                            }
                            doctorListArea.setText(sb.toString());
                        }
                    } else {
                        doctorListArea.setText("Erreur: Service web non disponible");
                    }
                } catch (Exception ex) {
                    doctorListArea.setText("Erreur lors de la recherche: " + ex.getMessage());
                }
            });

            specialitesGrid.add(specButton, i % 4, i / 4);
        }

        doctorListArea = new TextArea();
        doctorListArea.setEditable(false);
        styleTextArea(doctorListArea);

        // === SECTION RÉSERVATION ===
        VBox reservationSection = createStyledSection("Réserver un Rendez-vous");

        // Formulaire de réservation sur une ligne
        HBox reservationForm = new HBox(10);
        reservationForm.setAlignment(Pos.CENTER_LEFT);

        TextField doctorNameField = new TextField();
        doctorNameField.setPromptText("Nom du médecin");
        styleTextField(doctorNameField);

        TextField patientNameField = new TextField();
        patientNameField.setPromptText("Nom du patient");
        styleTextField(patientNameField);

        TextField dateField = new TextField();
        dateField.setPromptText("Date (dd/MM/yyyy HH:mm)");
        styleTextField(dateField);

        // Ajustement des largeurs
        doctorNameField.setPrefWidth(180);
        patientNameField.setPrefWidth(180);
        dateField.setPrefWidth(180);

        Button reserveButton = new Button("✅ Réserver");
        stylePrimaryButton(reserveButton);

        reservationForm.getChildren().addAll(doctorNameField, patientNameField, dateField, reserveButton);

        TextArea reservationResponseArea = new TextArea();
        reservationResponseArea.setEditable(false);
        styleTextArea(reservationResponseArea);

        // Action de réservation
        reserveButton.setOnAction(e -> {
            try {
                if (DocTime != null) {
                    String doctorName = doctorNameField.getText().trim();
                    String patientName = patientNameField.getText().trim();
                    String dateTime = dateField.getText().trim();
                    if (!doctorName.isEmpty() && !patientName.isEmpty() && !dateTime.isEmpty()) {
                        String response = DocTime.reserverRendezVous(doctorName, patientName, dateTime);
                        reservationResponseArea.setText(response);
                        // Effacer les champs après réservation
                        doctorNameField.clear();
                        patientNameField.clear();
                        dateField.clear();
                    } else {
                        reservationResponseArea.setText("Veuillez remplir tous les champs.");
                    }
                } else {
                    reservationResponseArea.setText("Erreur: Service web non disponible");
                }
            } catch (Exception ex) {
                reservationResponseArea.setText("Erreur lors de la réservation: " + ex.getMessage());
            }
        });

        // === SECTION ANNULATION ===
        VBox cancelSection = createStyledSection("Annuler un Rendez-vous");

        // Formulaire d'annulation sur une ligne
        HBox cancelForm = new HBox(10);
        cancelForm.setAlignment(Pos.CENTER_LEFT);

        TextField cancelDoctorField = new TextField();
        cancelDoctorField.setPromptText("Nom du médecin");
        styleTextField(cancelDoctorField);

        TextField cancelPatientField = new TextField();
        cancelPatientField.setPromptText("Nom du patient");
        styleTextField(cancelPatientField);

        TextField cancelDateField = new TextField();
        cancelDateField.setPromptText("Date (dd/MM/yyyy HH:mm)");
        styleTextField(cancelDateField);

        // Ajustement des largeurs
        cancelDoctorField.setPrefWidth(180);
        cancelPatientField.setPrefWidth(180);
        cancelDateField.setPrefWidth(180);

        Button cancelButton = new Button("❌ Annuler");
        styleCancelButton(cancelButton);

        cancelForm.getChildren().addAll(cancelDoctorField, cancelPatientField, cancelDateField, cancelButton);

        TextArea cancelResponseArea = new TextArea();
        cancelResponseArea.setEditable(false);
        styleTextArea(cancelResponseArea);

        // Action d'annulation
        cancelButton.setOnAction(e -> {
            try {
                if (DocTime != null) {
                    String doctorName = cancelDoctorField.getText().trim();
                    String patientName = cancelPatientField.getText().trim();
                    String dateTime = cancelDateField.getText().trim();
                    if (!doctorName.isEmpty() && !patientName.isEmpty() && !dateTime.isEmpty()) {
                        String response = DocTime.annulerRendezVousParCritere(doctorName, patientName, dateTime);
                        cancelResponseArea.setText(response);
                        // Effacer les champs après annulation
                        cancelDoctorField.clear();
                        cancelPatientField.clear();
                        cancelDateField.clear();
                    } else {
                        cancelResponseArea.setText("Veuillez remplir tous les champs.");
                    }
                } else {
                    cancelResponseArea.setText("Erreur: Service web non disponible");
                }
            } catch (Exception ex) {
                cancelResponseArea.setText("Erreur lors de l'annulation: " + ex.getMessage());
            }
        });

        // === SECTION CONSULTATION RENDEZ-VOUS ===
        VBox consultationSection = createStyledSection("Consultation des Rendez-vous");

        // Par patient
        HBox patientConsultForm = new HBox(10);
        patientConsultForm.setAlignment(Pos.CENTER_LEFT);

        TextField patientRdvField = new TextField();
        patientRdvField.setPromptText("Nom du patient");
        styleTextField(patientRdvField);
        patientRdvField.setPrefWidth(250);

        Button getRdvByPatientButton = new Button("👨‍⚕️ Voir par patient");
        styleSecondaryButton(getRdvByPatientButton);

        patientConsultForm.getChildren().addAll(patientRdvField, getRdvByPatientButton);

        TextArea patientRdvArea = new TextArea();
        patientRdvArea.setEditable(false);
        styleTextArea(patientRdvArea);

        // Action pour obtenir les rendez-vous par patient
        getRdvByPatientButton.setOnAction(e -> {
            try {
                if (DocTime != null) {
                    String patientName = patientRdvField.getText().trim();
                    if (!patientName.isEmpty()) {
                        String response = DocTime.getRendezvousByPatient(patientName);
                        patientRdvArea.setText(response);
                    }
                } else {
                    patientRdvArea.setText("Erreur: Service web non disponible");
                }
            } catch (Exception ex) {
                patientRdvArea.setText("Erreur: " + ex.getMessage());
            }
        });

        // Par médecin
        HBox doctorConsultForm = new HBox(10);
        doctorConsultForm.setAlignment(Pos.CENTER_LEFT);

        TextField doctorRdvField = new TextField();
        doctorRdvField.setPromptText("Nom du médecin");
        styleTextField(doctorRdvField);
        doctorRdvField.setPrefWidth(250);

        Button getRdvByDoctorButton = new Button("📋 Voir par médecin");
        styleSecondaryButton(getRdvByDoctorButton);

        doctorConsultForm.getChildren().addAll(doctorRdvField, getRdvByDoctorButton);

        TextArea doctorRdvArea = new TextArea();
        doctorRdvArea.setEditable(false);
        styleTextArea(doctorRdvArea);

        // Action pour obtenir les rendez-vous par médecin
        getRdvByDoctorButton.setOnAction(e -> {
            try {
                if (DocTime != null) {
                    String doctorName = doctorRdvField.getText().trim();
                    if (!doctorName.isEmpty()) {
                        String response = DocTime.getRendezvousByDoctor(doctorName);
                        doctorRdvArea.setText(response);
                    }
                } else {
                    doctorRdvArea.setText("Erreur: Service web non disponible");
                }
            } catch (Exception ex) {
                doctorRdvArea.setText("Erreur: " + ex.getMessage());
            }
        });

        // Assemblage final
        searchSection.getChildren().addAll(specialiteLabel, specialitesGrid, doctorListArea);
        reservationSection.getChildren().addAll(reservationForm, reservationResponseArea);
        cancelSection.getChildren().addAll(cancelForm, cancelResponseArea);
        consultationSection.getChildren().addAll(
                patientConsultForm, patientRdvArea,
                new Separator(),
                doctorConsultForm, doctorRdvArea
        );

        layout.getChildren().addAll(
                title,
                searchSection,
                reservationSection,
                cancelSection,
                consultationSection
        );

        // Configuration du ScrollPane
        mainScrollPane.setContent(layout);

        // Création de la scène et du stage
        Scene scene = new Scene(mainScrollPane, 900, 700);
        primaryStage.setTitle("Gestion des rendez-vous médicaux");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthodes de style réutilisables
    private VBox createStyledSection(String title) {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15));
        section.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");

        Label sectionTitle = new Label(title);
        sectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        section.getChildren().add(sectionTitle);

        return section;
    }

    private void styleTextField(TextField field) {
        field.setStyle("-fx-padding: 8px; -fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-border-radius: 4;");
    }

    private void styleTextArea(TextArea area) {
        area.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-padding: 10px;");
        area.setPrefHeight(120);
    }

    private void stylePrimaryButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    private void styleSecondaryButton(Button button) {
        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    private void styleCancelButton(Button button) {
        button.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
    }

    private void styleSpecialtyButton(Button button) {
        button.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-min-width: 120px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #5a6268; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-min-width: 120px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-min-width: 120px;"));
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de Connexion");
        alert.setHeaderText("Impossible de se connecter au service web");
        alert.setContentText(message);
        alert.showAndWait();
    }
}