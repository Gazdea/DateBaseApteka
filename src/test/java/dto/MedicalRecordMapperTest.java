package dto;

import junit.framework.TestCase;
import model.MedicalRecordBuilder;
import org.junit.jupiter.api.Test;

public class MedicalRecordMapperTest extends TestCase {


    @Test
    public void testToDTO() {
        // Setup
        final MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setRecord_id(0);
        medicalRecordDTO.setPatient_id(0);
        medicalRecordDTO.setRecord_details("record_details");

        final MedicalRecordBuilder expectedResult = new MedicalRecordBuilder.Builder()
                .setRecord_id(0)
                .setPatient_id(0)
                .setRecord_details("record_details")
                .build();

        // Run the test
        final MedicalRecordDTO result =  MedicalRecordMapper.toDTO(expectedResult);

        // Verify the results
        assertEquals(result.getRecord_details(), medicalRecordDTO.getRecord_details());
    }

    @Test
    public void testToEntity() {
        // Setup
        final MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setRecord_id(0);
        medicalRecordDTO.setPatient_id(0);
        medicalRecordDTO.setRecord_details("record_details");

        final MedicalRecordBuilder expectedResult = new MedicalRecordBuilder.Builder()
                .setRecord_id(0)
                .setPatient_id(0)
                .setRecord_details("record_details")
                .build();

        // Run the test
        final MedicalRecordBuilder result = MedicalRecordMapper.toEntity(medicalRecordDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

}
