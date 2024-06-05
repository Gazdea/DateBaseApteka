package dto;

import junit.framework.TestCase;
import model.PrescriptionBuilder;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PrescriptionMapperTest extends TestCase {

    @Test
    public void testToDTO() {
        // Setup
        final PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionID(0);
        prescriptionDTO.setPatientID(0);
        prescriptionDTO.setMedicationID(0);
        prescriptionDTO.setDate_of_prescribed(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        prescriptionDTO.setDosage("dosage");

        final PrescriptionBuilder expectedResult = new PrescriptionBuilder.Builder()
                .setPrescriptionID(0)
                .setDosage("dosage")
                .setPatientID(0)
                .setMedicationID(0)
                .setDate_of_prescribed(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build();

        // Run the test
        final PrescriptionDTO result = PrescriptionMapper.toDTO(expectedResult);

        // Verify the results
        assertEquals(prescriptionDTO.getDosage(), result.getDosage());
    }

    @Test
    public void testToEntity() {
        // Setup
        final PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionID(0);
        prescriptionDTO.setPatientID(0);
        prescriptionDTO.setMedicationID(0);
        prescriptionDTO.setDate_of_prescribed(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        prescriptionDTO.setDosage("dosage");

        final PrescriptionBuilder expectedResult = new PrescriptionBuilder.Builder()
                .setPrescriptionID(0)
                .setDosage("dosage")
                .setPatientID(0)
                .setMedicationID(0)
                .setDate_of_prescribed(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build();

        // Run the test
        final PrescriptionBuilder result = PrescriptionMapper.toEntity(prescriptionDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}