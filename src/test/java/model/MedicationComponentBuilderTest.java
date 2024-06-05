package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicationComponentBuilderTest extends TestCase {

    @Test
    public void testBuilder() {
        MedicationComponentBuilder.Builder builder = new MedicationComponentBuilder.Builder();
        builder.setMedicationId(1)
                .setComponentId(101);

        MedicationComponentBuilder medicationComponent = builder.build();

        assertEquals(1, medicationComponent.getMedicationId());
        assertEquals(101, medicationComponent.getComponentId());
    }

    @Test
    public void testEqualsAndHashCode() {
        MedicationComponentBuilder medicationComponent1 = new MedicationComponentBuilder.Builder()
                .setMedicationId(1)
                .setComponentId(101)
                .build();

        MedicationComponentBuilder medicationComponent2 = new MedicationComponentBuilder.Builder()
                .setMedicationId(1)
                .setComponentId(101)
                .build();

        assertEquals(medicationComponent1, medicationComponent2);
        assertEquals(medicationComponent1.hashCode(), medicationComponent2.hashCode());
    }

    @Test
    public void testToString() {
        MedicationComponentBuilder medicationComponent = new MedicationComponentBuilder.Builder()
                .setMedicationId(1)
                .setComponentId(101)
                .build();

        String expectedToString = "MedicationComponentBuilder{medicationId=1, componentId=101}";
        assertEquals(expectedToString, medicationComponent.toString());
    }
}