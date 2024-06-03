package dto;

import java.util.Date;

public class PatientDTO {
    private int patient_id;
    private String name;
    private Date birth_date;
    private MedicalRecordDTO medical_record;

    public PatientDTO(int patient_id, String name, Date birth_date, MedicalRecordDTO medical_record) {
        this.patient_id = patient_id;
        this.name = name;
        this.birth_date = birth_date;
        this.medical_record = medical_record;
    }

    public PatientDTO() {

    }

    public MedicalRecordDTO getMedical_record() {
        return medical_record;
    }

    public PatientDTO setMedical_record(MedicalRecordDTO medical_record) {
        this.medical_record = medical_record;
        return this;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public PatientDTO setPatient_id(int patient_id) {
        this.patient_id = patient_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PatientDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public PatientDTO setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
        return this;
    }
}
