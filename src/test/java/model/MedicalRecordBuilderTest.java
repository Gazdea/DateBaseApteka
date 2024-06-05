package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordBuilderTest extends TestCase {
    @Test
    public void testBuilder() {
        MedicalRecordBuilder.Builder builder = new MedicalRecordBuilder.Builder();
        builder.setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details");

        MedicalRecordBuilder medicalRecord = builder.build();

        assertEquals(1, medicalRecord.getRecord_id());
        assertEquals(101, medicalRecord.getPatient_id());
        assertEquals("Test record details", medicalRecord.getRecord_details());
    }

    @Test
    public void testEqualsAndHashCode() {
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

        assertEquals(medicalRecord1, medicalRecord2);
        assertEquals(medicalRecord1.hashCode(), medicalRecord2.hashCode());
    }

    @Test
    public void testToString() {
        MedicalRecordBuilder medicalRecord = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(101)
                .setRecord_details("Test record details")
                .build();

        String expectedToString = "MedicalrecordsBuilder{record_id=1, patient_id=101, record_details='Test record details'}";
        assertEquals(expectedToString, medicalRecord.toString());
    }
}