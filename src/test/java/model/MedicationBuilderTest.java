package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicationBuilderTest extends TestCase {

    @Test
    public void testBuilder() {
        List<ComponentBuilder> components = new ArrayList<>();
        components.add(new ComponentBuilder.Builder().setName("Component 1").build());
        components.add(new ComponentBuilder.Builder().setName("Component 2").build());

        MedicationBuilder.Builder builder = new MedicationBuilder.Builder();
        builder.setmMedication_id(1)
                .setName("Medication 1")
                .setDescription("Test description")
                .setComponents(components);

        MedicationBuilder medication = builder.build();

        assertEquals(1, medication.getmedication_id());
        assertEquals("Medication 1", medication.getName());
        assertEquals("Test description", medication.getDescription());
        assertEquals(components, medication.getComponents());
    }

    @Test
    public void testEqualsAndHashCode() {
        List<ComponentBuilder> components1 = new ArrayList<>();
        components1.add(new ComponentBuilder.Builder().setName("Component 1").build());
        components1.add(new ComponentBuilder.Builder().setName("Component 2").build());

        List<ComponentBuilder> components2 = new ArrayList<>();
        components2.add(new ComponentBuilder.Builder().setName("Component 1").build());
        components2.add(new ComponentBuilder.Builder().setName("Component 2").build());

        MedicationBuilder medication1 = new MedicationBuilder.Builder()
                .setmMedication_id(1)
                .setName("Medication 1")
                .setDescription("Test description")
                .setComponents(components1)
                .build();

        MedicationBuilder medication2 = new MedicationBuilder.Builder()
                .setmMedication_id(1)
                .setName("Medication 1")
                .setDescription("Test description")
                .setComponents(components2)
                .build();

        assertEquals(medication1, medication2);
        assertEquals(medication1.hashCode(), medication2.hashCode());
    }

    @Test
    public void testToString() {
        List<ComponentBuilder> components = new ArrayList<>();
        components.add(new ComponentBuilder.Builder().setName("Component 1").build());
        components.add(new ComponentBuilder.Builder().setName("Component 2").build());

        MedicationBuilder medication = new MedicationBuilder.Builder()
                .setmMedication_id(1)
                .setName("Medication 1")
                .setDescription("Test description")
                .setComponents(components)
                .build();

        String expectedToString = "MedicationBuilder{medication_id=1, name='Medication 1', description='Test description', components=" + components + "}";
        assertEquals(expectedToString, medication.toString());
    }
}