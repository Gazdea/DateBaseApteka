package dto;

import model.PatientBuilder;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class PatientMapperTest {

    @Test
    public void testToDTO() {
        // Setup
        final PatientDTO patientDTO = new PatientDTO();

        patientDTO.setPatient_id(0);
        patientDTO.setName("name");
        patientDTO.setBirth_date(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final PatientBuilder expectedResult = new PatientBuilder.Builder()
                .setPatientID(0)
                .setName("name")
                .setBirth_date(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build();

        // Run the test
        final PatientDTO result = PatientMapper.toDTO(expectedResult);

        // Verify the results
        assertEquals(patientDTO.getName(), expectedResult.getName());
    }

    @Test
    public void testToEntity() {
        // Setup
        final PatientDTO patientDTO = new PatientDTO();

        patientDTO.setPatient_id(0);
        patientDTO.setName("name");
        patientDTO.setBirth_date(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final PatientBuilder expectedResult = new PatientBuilder.Builder()
                .setPatientID(0)
                .setName("name")
                .setBirth_date(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build();

        // Run the test
        final PatientBuilder result = PatientMapper.toEntity(patientDTO);

        // Verify the results
        assertEquals(result, expectedResult);
    }
}
