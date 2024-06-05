package dto;

import junit.framework.TestCase;
import model.MedicationComponentBuilder;
import org.junit.jupiter.api.Test;

public class MedicationComponentMapperTest extends TestCase {

    @Test
    public void testToDTO() {
        // Setup
        final MedicationComponentDTO medicationComponent = new MedicationComponentDTO();
        medicationComponent.setMedicationId(0);
        medicationComponent.setComponentId(0);

        final MedicationComponentBuilder expectedResult = new MedicationComponentBuilder.Builder()
                .setComponentId(0)
                .setMedicationId(0)
                .build();

        // Run the test
        final MedicationComponentDTO result = MedicationComponentMapper.toDTO(expectedResult);

        // Verify the results
        assertEquals(medicationComponent.getMedicationId(), result.getComponentId());

    }

    @Test
    public void testToEntity() {
        // Setup
        final MedicationComponentDTO medicationComponent = new MedicationComponentDTO();
        medicationComponent.setMedicationId(0);
        medicationComponent.setComponentId(0);

        final MedicationComponentBuilder expectedResult = new MedicationComponentBuilder.Builder()
                .setComponentId(0)
                .setMedicationId(0)
                .build();

        // Run the test
        final MedicationComponentBuilder result = MedicationComponentMapper.toEntity(medicationComponent);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}