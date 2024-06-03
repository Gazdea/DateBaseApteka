package model;

import java.util.Date;
import java.util.Objects;

public class PatientBuilder {
    private int patientID;
    private String name;
    private Date birth_date;
    private MedicalRecordBuilder medicalRecord;

    public  PatientBuilder(Builder builder) {
        this.patientID = builder.patientID;
        this.name = builder.name;
        this.birth_date = builder.birth_date;
        this.medicalRecord = builder.medicalRecord;
    }

    @Override
    public String toString() {
        return "PatientBuilder{" +
                "patientID=" + patientID +
                ", name='" + name + '\'' +
                ", birth_date=" + birth_date +
                ", medicalRecord=" + medicalRecord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PatientBuilder that = (PatientBuilder) o;
        return patientID == that.patientID && name.equals(that.name) && birth_date.equals(that.birth_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientID, name, birth_date);
    }

    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public MedicalRecordBuilder getMedicalRecord() {
        return medicalRecord;
    }

    public static class Builder {
        private int patientID;
        private String name;
        private Date birth_date;
        private MedicalRecordBuilder medicalRecord;

        public Builder setMedicalRecord(MedicalRecordBuilder medicalRecord) {
            this.medicalRecord = medicalRecord;
            return this;
        }

        public Builder setPatientID(int patientID) {
            this.patientID = patientID;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBirth_date(Date birth_date) {
            this.birth_date = birth_date;
            return this;
        }

        public PatientBuilder build() {
            return new PatientBuilder(this);
        }
    }


}
