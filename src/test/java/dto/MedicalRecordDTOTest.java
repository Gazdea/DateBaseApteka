package dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class MedicalRecordDTOTest extends TestCase {

        MedicalRecordDTO medicalRecordDTOUnderTest = new MedicalRecordDTO();
    @Test
    public void testRecord_idGetterAndSetter() {
        final int record_id = 0;
        medicalRecordDTOUnderTest.setRecord_id(record_id);
        assertEquals(record_id, medicalRecordDTOUnderTest.getRecord_id());
    }

    @Test
    public void testPatient_idGetterAndSetter() {
        final int patient_id = 0;
        medicalRecordDTOUnderTest.setPatient_id(patient_id);
        assertEquals(patient_id, medicalRecordDTOUnderTest.getPatient_id());
    }

    @Test
    public void testRecord_detailsGetterAndSetter() {
        final String record_details = "record_details";
        medicalRecordDTOUnderTest.setRecord_details(record_details);
        assertEquals(record_details, medicalRecordDTOUnderTest.getRecord_details());
    }

    @Test
    public void testRecord_idGetterAndSetter1() {
        final int record_id = 0;
        medicalRecordDTOUnderTest.setRecord_id(record_id);
        assertEquals(record_id, medicalRecordDTOUnderTest.getRecord_id());
    }

}