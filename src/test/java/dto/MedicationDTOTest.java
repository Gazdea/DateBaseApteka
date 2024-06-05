package dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicationDTOTest {

    private MedicationDTO medicationDTOUnderTest = new MedicationDTO();

    @Test
    void testComponentsGetterAndSetter() {
        final List<ComponentDTO> components = Arrays.asList(new ComponentDTO());
        medicationDTOUnderTest.setComponents(components);
        assertEquals(components, medicationDTOUnderTest.getComponents());
    }

    @Test
    void testDescriptionGetterAndSetter() {
        final String description = "description";
        medicationDTOUnderTest.setDescription(description);
        assertEquals(description, medicationDTOUnderTest.getDescription());
    }

    @Test
    void testMedication_idGetterAndSetter() {
        final int medication_id = 0;
        medicationDTOUnderTest.setMedication_id(medication_id);
        assertEquals(medication_id, medicationDTOUnderTest.getMedication_id());
    }

    @Test
    void testNameGetterAndSetter() {
        final String name = "name";
        medicationDTOUnderTest.setName(name);
        assertEquals(name, medicationDTOUnderTest.getName());
    }
}
