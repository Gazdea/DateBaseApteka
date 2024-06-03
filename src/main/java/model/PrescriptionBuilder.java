package model;

import java.util.Date;
import java.util.Objects;

public class PrescriptionBuilder {
    private int prescriptionID;
    private int patientID;
    private int medicationID;
    private Date date_of_prescribed;
    private String dosage;

    public  PrescriptionBuilder(Builder builder) {
        this.prescriptionID = builder.prescriptionID;
        this.patientID = builder.patientID;
        this.medicationID = builder.medicationID;
        this.date_of_prescribed = builder.date_of_prescribed;
        this.dosage = builder.dosage;
    }

    @Override
    public String toString() {
        return "PrescriptionBuilder [prescriptionID=" + prescriptionID + ", patientID=" + patientID
                + ", medicationID=" + medicationID + ", date_of_prescribed=" + date_of_prescribed
                + ", dosage=" + dosage + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PrescriptionBuilder that = (PrescriptionBuilder) o;
        return prescriptionID == that.prescriptionID && patientID == that.patientID
                && medicationID == that.medicationID && date_of_prescribed.equals(that.date_of_prescribed)
                && dosage.equals(that.dosage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionID, patientID, medicationID, date_of_prescribed, dosage);
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getMedicationID() {
        return medicationID;
    }

    public Date getDate_of_prescribed() {
        return date_of_prescribed;
    }

    public PrescriptionBuilder setDate_of_prescribed(java.sql.Date date_of_prescribed) {
        this.date_of_prescribed = date_of_prescribed;
        return this;
    }

    public String getDosage() {
        return dosage;
    }

    public static class Builder {
        private int prescriptionID;
        private int patientID;
        private int medicationID;
        private Date date_of_prescribed;
        private String dosage;

        public Builder setPrescriptionID(int prescriptionID) {
            this.prescriptionID = prescriptionID;
            return this;
        }

        public Builder setPatientID(int patientID) {
            this.patientID = patientID;
            return this;
        }

        public Builder setMedicationID(int medicationID) {
            this.medicationID = medicationID;
            return this;
        }

        public Builder setDate_of_prescribed(Date date_of_prescribed) {
            this.date_of_prescribed = date_of_prescribed;
            return this;
        }

        public Builder setDosage(String dosage) {
            this.dosage = dosage;
            return this;
        }

        public PrescriptionBuilder build() {
            return new PrescriptionBuilder(this);
        }
    }
}
