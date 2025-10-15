package org.sid;

import com.raps.code.generate.ws.Medecin;
import com.raps.code.generate.ws.DocTime;
import com.raps.code.generate.ws.DocTime_Service;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crée une instance du service et obtient le port
        DocTime_Service docTimeService = new DocTime_Service();
        DocTime docTimePort = docTimeService.getDocTimePort();  // Obtenir le port

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Lister les médecins par spécialité");
            System.out.println("2. Réserver un rendez-vous");
            System.out.println("3. Afficher les rendez-vous d'un médecin");
            System.out.println("4. Afficher les rendez-vous d'un patient");
            System.out.println("5. Annuler un rendez-vous");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la ligne de nouvelle ligne après le choix

            switch (choice) {
                case 1:
                    // Lister les médecins par spécialité
                    System.out.print("Entrez la spécialité (ex: Cardiologie) : ");
                    String specialite = scanner.nextLine();
                    try {
                        List<Medecin> medecins = docTimePort.getMedecinsParSpecialite(specialite);
                        if (medecins.isEmpty()) {
                            System.out.println("Aucun médecin trouvé pour la spécialité " + specialite);
                        } else {
                            System.out.println("Médecins trouvés :");
                            medecins.forEach(medecin -> System.out.println("• " + medecin.getNom() + " : " + medecin.getSpecialite()));
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la recherche : " + e.getMessage());
                    }
                    break;
                case 2:
                    // Réserver un rendez-vous
                    System.out.print("Entrez le nom du médecin : ");
                    String doctorName = scanner.nextLine();
                    System.out.print("Entrez le nom du patient : ");
                    String patientName = scanner.nextLine();
                    System.out.print("Entrez la date et l'heure du rendez-vous (format : dd/MM/yyyy HH:mm) : ");
                    String dateTime = scanner.nextLine();
                    try {
                        String reservationResponse = docTimePort.reserverRendezVous(doctorName, patientName, dateTime);
                        System.out.println(reservationResponse);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la réservation : " + e.getMessage());
                    }
                    break;
                case 3:
                    // Afficher les rendez-vous d'un médecin
                    System.out.print("Entrez le nom du médecin : ");
                    String doctor = scanner.nextLine();
                    try {
                        String doctorAppointments = docTimePort.getRendezvousByDoctor(doctor);
                        System.out.println(doctorAppointments);
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 4:
                    // Afficher les rendez-vous d'un patient
                    System.out.print("Entrez le nom du patient : ");
                    String patient = scanner.nextLine();
                    try {
                        String patientAppointments = docTimePort.getRendezvousByPatient(patient);
                        System.out.println(patientAppointments);
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 5:
                    // Annuler un rendez-vous
                    System.out.print("Entrez le nom du médecin : ");
                    String medecinName = scanner.nextLine();
                    System.out.print("Entrez le nom du patient : ");
                    String patientToCancel = scanner.nextLine();
                    System.out.print("Entrez la date du rendez-vous (format : dd/MM/yyyy HH:mm) : ");
                    String dateToCancel = scanner.nextLine();
                    try {
                        String cancellationResponse = docTimePort.annulerRendezVousParCritere(medecinName, patientToCancel, dateToCancel);
                        System.out.println(cancellationResponse);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'annulation : " + e.getMessage());
                    }
                    break;
                case 6:
                    // Quitter le programme
                    System.out.println("Merci d'utiliser notre service. À bientôt!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }
}