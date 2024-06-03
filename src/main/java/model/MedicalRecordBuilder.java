package model;

import java.util.Objects;

public class MedicalRecordBuilder {
    private final int record_id;
    private final int patient_id;
    private final String record_details;

    public MedicalRecordBuilder(Builder builder) {
        this.record_id = builder.record_id;
        this.patient_id = builder.patient_id;
        this.record_details = builder.record_details;
    }

    @Override
    public String toString() {
        return "MedicalrecordsBuilder{" +
                "record_id=" + record_id +
                ", patient_id=" + patient_id +
                ", record_details='" + record_details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecordBuilder that = (MedicalRecordBuilder) o;
        return record_id == that.record_id && patient_id == that.patient_id && Objects.equals(record_details, that.record_details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record_id, patient_id, record_details);
    }

    public int getRecord_id() {
        return record_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public String getRecord_details() {
        return record_details;
    }

    public static class Builder {
        private int record_id;
        private int patient_id;
        private String record_details;

        public Builder() {
        }

        public Builder setRecord_id(int record_id) {
            this.record_id = record_id;
            return this;
        }

        public Builder setPatient_id(int patient_id) {
            this.patient_id = patient_id;
            return this;
        }

        public Builder setRecord_details(String record_details) {
            this.record_details = record_details;
            return this;
        }

        public MedicalRecordBuilder build() {
            return new MedicalRecordBuilder(this);
        }
    }

}
