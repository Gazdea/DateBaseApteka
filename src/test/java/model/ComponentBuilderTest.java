package model;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;


public class ComponentBuilderTest {

    @Test
    public void testBuilder() {
        ComponentBuilder.Builder builder = new ComponentBuilder.Builder();
        builder.setComponent_id(1)
                .setName("Test Component")
                .setDescription("This is a test component");

        ComponentBuilder component = builder.build();

        assertEquals(1, component.getComponent_id());
        assertEquals("Test Component", component.getName());
        assertEquals("This is a test component", component.getDescription());
    }

    @Test
    public void testEqualsAndHashCode() {
        ComponentBuilder component1 = new ComponentBuilder.Builder()
                .setComponent_id(1)
                .setName("Test Component")
                .setDescription("This is a test component")
                .build();

        ComponentBuilder component2 = new ComponentBuilder.Builder()
                .setComponent_id(1)
                .setName("Test Component")
                .setDescription("This is a test component")
                .build();

        assertEquals(component1, component2);
        assertEquals(component1.hashCode(), component2.hashCode());
    }

    @Test
    public void testToString() {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setComponent_id(1)
                .setName("Test Component")
                .setDescription("This is a test component")
                .build();

        String expectedToString = "ComponentBuilder{component_id=1, name='Test Component', description='This is a test component'}";
        assertEquals(expectedToString, component.toString());
    }
}
