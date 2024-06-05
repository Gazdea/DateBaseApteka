package dto;

import junit.framework.TestCase;
import model.MedicationBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MedicationMapperTest extends TestCase {
    @Test
    public void testToDTO() {
        // Setup
        final MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setDescription("description");
        medicationDTO.setName("name");

        final MedicationBuilder expectedResult = new MedicationBuilder.Builder()
                .setDescription("description")
                .setName("name")
                .build();

        // Run the test
        final MedicationDTO result = MedicationMapper.toDTO(expectedResult);

        // Verify the results
        assertEquals(medicationDTO.getName(), result.getName());
    }

    @Test
    public void testToEntity() {
        // Setup
        final MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setDescription("description");
        medicationDTO.setName("name");

        final MedicationBuilder expectedResult = new MedicationBuilder.Builder()
                .setDescription("description")
                .setName("name")
                .build();

        // Run the test
        final MedicationBuilder result = MedicationMapper.toEntity(medicationDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}