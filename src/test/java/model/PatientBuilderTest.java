package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientBuilderTest extends TestCase {

    @Test
    public void testBuilder() {
        Date birthDate = new Date();

        MedicalRecordBuilder medicalRecord = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details")
                .build();

        PatientBuilder.Builder builder = new PatientBuilder.Builder();
        builder.setPatientID(1)
                .setName("John Doe")
                .setBirth_date(birthDate)
                .setMedicalRecord(medicalRecord);

        PatientBuilder patient = builder.build();

        assertEquals(1, patient.getPatientID());
        assertEquals("John Doe", patient.getName());
        assertEquals(birthDate, patient.getBirth_date());
        assertEquals(medicalRecord, patient.getMedicalRecord());
    }

    @Test
    public void testEqualsAndHashCode() {
        Date birthDate = new Date();

        MedicalRecordBuilder medicalRecord1 = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details")
                .build();

        MedicalRecordBuilder medicalRecord2 = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details")
                .build();

        PatientBuilder patient1 = new PatientBuilder.Builder()
                .setPatientID(1)
                .setName("John Doe")
                .setBirth_date(birthDate)
                .setMedicalRecord(medicalRecord1)
                .build();

        PatientBuilder patient2 = new PatientBuilder.Builder()
                .setPatientID(1)
                .setName("John Doe")
                .setBirth_date(birthDate)
                .setMedicalRecord(medicalRecord2)
                .build();

        assertEquals(patient1, patient2);
        assertEquals(patient1.hashCode(), patient2.hashCode());
    }

    @Test
    public void testToString() {
        Date birthDate = new Date();

        MedicalRecordBuilder medicalRecord = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details")
                .build();

        PatientBuilder patient = new PatientBuilder.Builder()
                .setPatientID(1)
                .setName("John Doe")
                .setBirth_date(birthDate)
                .setMedicalRecord(medicalRecord)
                .build();

        String expectedToString = "PatientBuilder{patientID=1, name='John Doe', birth_date=" + birthDate + ", medicalRecord=" + medicalRecord + "}";
        assertEquals(expectedToString, patient.toString());
    }
}