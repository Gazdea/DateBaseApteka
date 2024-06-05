package dto;

import junit.framework.TestCase;
import model.ComponentBuilder;
import org.junit.jupiter.api.Test;

public class ComponentMapperTest extends TestCase {

    @Test
    public void testToDTO() {
        final ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponent_id(0);
        componentDTO.setName("name");
        componentDTO.setDescription("description");

        final ComponentBuilder expectedResult = new ComponentBuilder.Builder()
                .setComponent_id(0)
                .setName("name")
                .setDescription("description")
                .build();

        final ComponentDTO result = ComponentMapper.toDTO(expectedResult);

        assertEquals(componentDTO.getName(), result.getName());
        assertEquals(componentDTO.getDescription(), result.getDescription());
    }

    @Test
    public void testToEntity() {
        final ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponent_id(0);
        componentDTO.setName("name");
        componentDTO.setDescription("description");

        final ComponentBuilder expectedResult = new ComponentBuilder.Builder()
                .setComponent_id(0)
                .setName("name")
                .setDescription("description")
                .build();

        final ComponentBuilder result = ComponentMapper.toEntity(componentDTO);

        assertEquals(expectedResult, result);
    }

}