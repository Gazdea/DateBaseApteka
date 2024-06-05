package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionBuilderTest extends TestCase {
    @Test
    public void testBuilder() {
        Date dateOfPrescribed = new Date();

        PrescriptionBuilder.Builder builder = new PrescriptionBuilder.Builder();
        builder.setPrescriptionID(1)
                .setPatientID(101)
                .setMedicationID(201)
                .setDate_of_prescribed(dateOfPrescribed)
                .setDosage("1 pill per day");

        PrescriptionBuilder prescription = builder.build();

        assertEquals(1, prescription.getPrescriptionID());
        assertEquals(101, prescription.getPatientID());
        assertEquals(201, prescription.getMedicationID());
        assertEquals(dateOfPrescribed, prescription.getDate_of_prescribed());
        assertEquals("1 pill per day", prescription.getDosage());
    }

    @Test
    public void testEqualsAndHashCode() {
        Date dateOfPrescribed = new Date();

        PrescriptionBuilder prescription1 = new PrescriptionBuilder.Builder()
                .setPrescriptionID(1)
                .setPatientID(101)
                .setMedicationID(201)
                .setDate_of_prescribed(dateOfPrescribed)
                .setDosage("1 pill per day")
                .build();

        PrescriptionBuilder prescription2 = new PrescriptionBuilder.Builder()
                .setPrescriptionID(1)
                .setPatientID(101)
                .setMedicationID(201)
                .setDate_of_prescribed(dateOfPrescribed)
                .setDosage("1 pill per day")
                .build();

        assertEquals(prescription1, prescription2);
        assertEquals(prescription1.hashCode(), prescription2.hashCode());
    }

    @Test
    public void testToString() {
        Date dateOfPrescribed = new Date();

        PrescriptionBuilder prescription = new PrescriptionBuilder.Builder()
                .setPrescriptionID(1)
                .setPatientID(101)
                .setMedicationID(201)
                .setDate_of_prescribed(dateOfPrescribed)
                .setDosage("1 pill per day")
                .build();

        String expectedToString = "PrescriptionBuilder [prescriptionID=1, patientID=101, medicationID=201, date_of_prescribed=" + dateOfPrescribed + ", dosage=1 pill per day]";
        assertEquals(expectedToString, prescription.toString());
    }
}