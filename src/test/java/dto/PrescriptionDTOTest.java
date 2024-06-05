package dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PrescriptionDTOTest extends TestCase {
    PrescriptionDTO prescriptionDTOUnderTest = new PrescriptionDTO();
    @Test
    public void testDate_of_prescribedGetterAndSetter() {
        final Date date_of_prescribed = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        prescriptionDTOUnderTest.setDate_of_prescribed(date_of_prescribed);
        assertEquals(date_of_prescribed, prescriptionDTOUnderTest.getDate_of_prescribed());
    }

    @Test
    public void testDosageGetterAndSetter() {
        final String dosage = "dosage";
        prescriptionDTOUnderTest.setDosage(dosage);
        assertEquals(dosage, prescriptionDTOUnderTest.getDosage());
    }

    @Test
    public void testMedicationIDGetterAndSetter() {
        final int medicationID = 0;
        prescriptionDTOUnderTest.setMedicationID(medicationID);
        assertEquals(medicationID, prescriptionDTOUnderTest.getMedicationID());
    }

    @Test
    public void testPatientIDGetterAndSetter() {
        final int patientID = 0;
        prescriptionDTOUnderTest.setPatientID(patientID);
        assertEquals(patientID, prescriptionDTOUnderTest.getPatientID());
    }

    @Test
    public void testPrescriptionIDGetterAndSetter() {
        final int prescriptionID = 0;
        prescriptionDTOUnderTest.setPrescriptionID(prescriptionID);
        assertEquals(prescriptionID, prescriptionDTOUnderTest.getPrescriptionID());
    }
}