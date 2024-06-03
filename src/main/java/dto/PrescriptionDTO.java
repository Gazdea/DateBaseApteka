package dto;

import java.util.Date;

public class PrescriptionDTO {
    private int prescriptionID;
    private int patientID;
    private int medicationID;
    private Date date_of_prescribed;
    private String dosage;

    public PrescriptionDTO() {

    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public PrescriptionDTO setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
        return this;
    }

    public int getPatientID() {
        return patientID;
    }

    public PrescriptionDTO setPatientID(int patientID) {
        this.patientID = patientID;
        return this;
    }

    public int getMedicationID() {
        return medicationID;
    }

    public PrescriptionDTO setMedicationID(int medicationID) {
        this.medicationID = medicationID;
        return this;
    }

    public Date getDate_of_prescribed() {
        return date_of_prescribed;
    }

    public PrescriptionDTO setDate_of_prescribed(Date date_of_prescribed) {
        this.date_of_prescribed = date_of_prescribed;
        return this;
    }

    public String getDosage() {
        return dosage;
    }

    public PrescriptionDTO setDosage(String dosage) {
        this.dosage = dosage;
        return this;
    }
}
