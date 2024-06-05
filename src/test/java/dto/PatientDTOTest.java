package dto;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientDTOTest {

    private PatientDTO patientDTOUnderTest = new PatientDTO();

    @Test
    void testBirth_dateGetterAndSetter() {
        final Date birth_date = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        patientDTOUnderTest.setBirth_date(birth_date);
        assertEquals(birth_date, patientDTOUnderTest.getBirth_date());
    }

    @Test
    void testMedical_recordGetterAndSetter() {
        final MedicalRecordDTO medical_record = new MedicalRecordDTO();
        patientDTOUnderTest.setMedical_record(medical_record);
        assertEquals(medical_record, patientDTOUnderTest.getMedical_record());
    }

    @Test
    void testNameGetterAndSetter() {
        final String name = "name";
        patientDTOUnderTest.setName(name);
        assertEquals(name, patientDTOUnderTest.getName());
    }

    @Test
    void testPatient_idGetterAndSetter() {
        final int patient_id = 0;
        patientDTOUnderTest.setPatient_id(patient_id);
        assertEquals(patient_id, patientDTOUnderTest.getPatient_id());
    }
}
