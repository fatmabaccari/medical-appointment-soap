package service;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.Medecin;
import model.Rendezvous;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "DocTime")
public class DocTime {

    private List<Rendezvous> rendezvousList = new ArrayList<>();
    private List<Medecin> medecinsList = new ArrayList<>();

    public DocTime() {
// Liste complète de médecins par spécialité
        medecinsList.add(new Medecin("1", "Ahmed Ben Ali", "Médecine générale"));
        medecinsList.add(new Medecin("2", "Fatma Zahra", "Médecine générale"));
        medecinsList.add(new Medecin("3", "Youssef El Idrissi", "Médecine générale"));
        medecinsList.add(new Medecin("4", "Soukaina Ben Ahmed", "Médecine générale"));
        medecinsList.add(new Medecin("5", "Rania Chraibi", "Médecine générale"));

// 🫀 Cardiologie
        medecinsList.add(new Medecin("11", "Hicham El Alaoui", "Cardiologie"));
        medecinsList.add(new Medecin("12", "Leila Ben Moussa", "Cardiologie"));
        medecinsList.add(new Medecin("13", "Amine Kadiri", "Cardiologie"));
        medecinsList.add(new Medecin("14", "Nour Eddine Fakih", "Cardiologie"));

// 🧠 Neurologie
        medecinsList.add(new Medecin("21", "Akram Ben Ali", "Neurologie"));
        medecinsList.add(new Medecin("22", "Sonia El Idrissi", "Neurologie"));
        medecinsList.add(new Medecin("23", "Mouna Tahiri", "Neurologie"));

// 👁️ Ophtalmologie
        medecinsList.add(new Medecin("31", "Souad El Gharbi", "Ophtalmologie"));
        medecinsList.add(new Medecin("32", "Ali Ben Salah", "Ophtalmologie"));
        medecinsList.add(new Medecin("33", "Hassan Bennis", "Ophtalmologie"));

// 👶 Pédiatrie
        medecinsList.add(new Medecin("41", "Amina Ben Abdallah", "Pédiatrie"));
        medecinsList.add(new Medecin("42", "Karim El Fassi", "Pédiatrie"));
        medecinsList.add(new Medecin("43", "Lamia Bensaid", "Pédiatrie"));

// ⚕️ Gynécologie & Obstétrique
        medecinsList.add(new Medecin("51", "Salma Ben Ali", "Gynécologie"));
        medecinsList.add(new Medecin("52", "Nadia El Alaoui", "Gynécologie"));
        medecinsList.add(new Medecin("53", "Rachida Lahlou", "Gynécologie"));

// 💊 Dermatologie
        medecinsList.add(new Medecin("61", "Youssef El Idrissi", "Dermatologie"));
        medecinsList.add(new Medecin("62", "Sanae Ben Othman", "Dermatologie"));

// 🦴 Orthopédie
        medecinsList.add(new Medecin("71", "Mohamed Amrani", "Orthopédie"));
        medecinsList.add(new Medecin("72", "Imane Cherkaoui", "Orthopédie"));

// 🧬 Oncologie
        medecinsList.add(new Medecin("81", "Sami Kabbaj", "Oncologie"));
        medecinsList.add(new Medecin("82", "Meryem Benhima", "Oncologie"));

// 💨 Pneumologie
        medecinsList.add(new Medecin("91", "Hassan El Mahdi", "Pneumologie"));
        medecinsList.add(new Medecin("92", "Houda Azzouz", "Pneumologie"));

// 🍽️ Gastro-entérologie
        medecinsList.add(new Medecin("101", "Omar Chafik", "Gastro-entérologie"));
        medecinsList.add(new Medecin("102", "Samira Hadi", "Gastro-entérologie"));

// 🧂 Endocrinologie
        medecinsList.add(new Medecin("111", "Imad Berrada", "Endocrinologie"));
        medecinsList.add(new Medecin("112", "Nawal Idrissi", "Endocrinologie"));

// 💉 Chirurgie générale
        medecinsList.add(new Medecin("121", "Ali Ben Salah", "Chirurgie"));
        medecinsList.add(new Medecin("122", "Mounir Rahmouni", "Chirurgie"));

// 🧘‍♀️ Psychiatrie
        medecinsList.add(new Medecin("131", "Hind Lahlou", "Psychiatrie"));
        medecinsList.add(new Medecin("132", "Khalid Amzil", "Psychiatrie"));

// 🔬 Radiologie
        medecinsList.add(new Medecin("141", "Yasmine Bennis", "Radiologie"));
        medecinsList.add(new Medecin("142", "Abderrahim Tazi", "Radiologie"));

// 🚨 Médecine d'urgence
        medecinsList.add(new Medecin("151", "Laila Souissi", "Médecine d'urgence"));
        medecinsList.add(new Medecin("152", "Youssef Aït Omar", "Médecine d'urgence"));
    }
    @WebMethod
    public List<Medecin> getMedecinsParSpecialite(@WebParam(name = "specialite") String specialite) {
        List<Medecin> result = new ArrayList<>();
        for (Medecin medecin : medecinsList) {
            if (medecin.getSpecialite().equalsIgnoreCase(specialite.trim())) {
                result.add(medecin);
            }
        }
        return result;
    }

    @WebMethod
    public String getRendezvousByDoctor(@WebParam(name = "doctorName") String doctorName) {
        List<Rendezvous> result = new ArrayList<>();
        StringBuilder response = new StringBuilder("Recherche des rendez-vous pour le médecin : ").append(doctorName).append("\n");

        for (Rendezvous rendezvous : rendezvousList) {
            if (rendezvous.getDoctorName().equalsIgnoreCase(doctorName.trim())) {
                result.add(rendezvous);
            }
        }

        if (result.isEmpty()) {
            response.append("Aucun rendez-vous trouvé pour le médecin ").append(doctorName);
        } else {
            for (Rendezvous rendezvous : result) {
                response.append("Patient : ").append(rendezvous.getPatient())
                        .append(" - Date : ").append(rendezvous.getDate()).append("\n");
            }
        }
        return response.toString();
    }

    @WebMethod
    public String getRendezvousByPatient(@WebParam(name = "patientName") String patientName) {
        List<Rendezvous> result = new ArrayList<>();
        StringBuilder response = new StringBuilder("Recherche des rendez-vous pour le patient : ").append(patientName).append("\n");

        for (Rendezvous rendezvous : rendezvousList) {
            if (rendezvous.getPatient().equalsIgnoreCase(patientName.trim())) {
                result.add(rendezvous);
            }
        }

        if (result.isEmpty()) {
            response.append("Aucun rendez-vous trouvé pour le patient ").append(patientName);
        } else {
            for (Rendezvous rendezvous : result) {
                response.append("Médecin : ").append(rendezvous.getDoctorName())
                        .append(" - Date : ").append(rendezvous.getDate()).append("\n");
            }
        }
        return response.toString();
    }



    @WebMethod
    public String reserverRendezVous(@WebParam(name = "doctorName") String doctorName,
                                     @WebParam(name = "patientName") String patientName,
                                     @WebParam(name = "dateTime") String dateTime) {

        // Validate if the doctor exists
        boolean doctorExists = medecinsList.stream()
                .anyMatch(m -> m.getNom().equalsIgnoreCase(doctorName.trim()));
        if (!doctorExists) {
            return "Le médecin " + doctorName + " n'existe pas dans la liste des médecins.";
        }

        // Sanitize the input to ensure proper formatting
        String[] dateParts = dateTime.trim().split(" ");
        String[] dateComponents = dateParts[0].split("/");

        // Pad day and month with leading zero if needed
        if (dateComponents[0].length() == 1) {
            dateComponents[0] = "0" + dateComponents[0]; // Pad day
        }
        if (dateComponents[1].length() == 1) {
            dateComponents[1] = "0" + dateComponents[1]; // Pad month
        }

        // Rebuild the date string
        String formattedDateTime = String.join("/", dateComponents) + " " + dateParts[1];

        // Validate the date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"); // Updated format
        LocalDateTime rendezvousDateTime;
        try {
            rendezvousDateTime = LocalDateTime.parse(formattedDateTime, formatter);
        } catch (Exception e) {
            return "Format de la date invalide. Assurez-vous que la date est au format dd/MM/yyyy HH:mm";
        }

        // Check if the time is before 18:00
        if (rendezvousDateTime.getHour() >= 18) {
            return "Les rendez-vous doivent être réservés avant 18h.";
        }

        // Ensure the date is in the future
        if (rendezvousDateTime.isBefore(LocalDateTime.now())) {
            return "La date et l'heure du rendez-vous doivent être dans le futur.";
        }

        // Check if the doctor is available at that time
        for (Rendezvous rendezvous : rendezvousList) {
            if (rendezvous.getDoctorName().trim().equalsIgnoreCase(doctorName.trim()) &&
                    rendezvous.getDate().trim().equals(formattedDateTime.trim())) {
                return "Le médecin " + doctorName + " n'est pas disponible à ce moment-là. Veuillez choisir un autre horaire.";
            }
        }

        // If everything is valid, make the reservation
        Rendezvous newRendezvous = new Rendezvous("Rendezvous" + (rendezvousList.size() + 1), formattedDateTime.trim(), patientName.trim(),
                medecinsList.stream().filter(m -> m.getNom().equalsIgnoreCase(doctorName.trim())).findFirst().orElse(null));
        rendezvousList.add(newRendezvous);
        return "Rendez-vous réservé avec succès : " + newRendezvous.getId();
    }

    @WebMethod
    public String annulerRendezVousParCritere(@WebParam(name = "medecin") String medecinNom,
                                              @WebParam(name = "patient") String patientNom,
                                              @WebParam(name = "date") String dateRendezvous) {

        // Recherche du rendez-vous correspondant au médecin, patient et date
        Rendezvous rendezvousAAnnuler = null;
        for (Rendezvous rendezvous : rendezvousList) {
            if (rendezvous.getMedecin().getNom().equalsIgnoreCase(medecinNom.trim()) &&
                    rendezvous.getPatient().equalsIgnoreCase(patientNom.trim()) &&
                    rendezvous.getDate().equalsIgnoreCase(dateRendezvous.trim())) {
                rendezvousAAnnuler = rendezvous;
                break;
            }
        }

        if (rendezvousAAnnuler != null) {
            rendezvousList.remove(rendezvousAAnnuler);
            return "Rendez-vous annulé avec succès : " + rendezvousAAnnuler.getId();
        } else {
            return "Aucun rendez-vous trouvé pour le médecin " + medecinNom + ", le patient " + patientNom +
                    " à la date " + dateRendezvous + ".";
        }
    }

}
