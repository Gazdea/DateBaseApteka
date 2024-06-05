package dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class MedicationComponentDTOTest extends TestCase {
    MedicationComponentDTO medicationComponentDTOUnderTest = new MedicationComponentDTO();

    @Test
    public void testComponentIdGetterAndSetter() {
        final int componentId = 0;
        medicationComponentDTOUnderTest.setComponentId(componentId);
        assertEquals(componentId, medicationComponentDTOUnderTest.getComponentId());
    }

    @Test
    public void testMedicationIdGetterAndSetter() {
        final int medicationId = 0;
        medicationComponentDTOUnderTest.setMedicationId(medicationId);
        assertEquals(medicationId, medicationComponentDTOUnderTest.getMedicationId());
    }

}