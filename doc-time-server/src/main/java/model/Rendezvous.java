package model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rendezvous {
    private String id;
    private String date;
    private String patient;
    private Medecin medecin;

    public Rendezvous() {
    }

    public Rendezvous(String id, String date, String patient, Medecin medecin) {
        this.id = id;
        this.date = date;
        this.patient = patient;
        this.medecin = medecin;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement
    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    @XmlElement
    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    // Ajoutez cette méthode pour obtenir le nom du médecin
    public String getDoctorName() {
        return medecin != null ? medecin.getNom() : null;
    }
}
