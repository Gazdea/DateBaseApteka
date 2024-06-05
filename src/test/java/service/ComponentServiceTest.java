package service;


import dao.ComponentDAO;
import dto.ComponentDTO;
import model.ComponentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;



@ExtendWith(MockitoExtension.class)
public class ComponentServiceTest {
    @Mock
    private ComponentDAO componentDAO;
    @InjectMocks
    private ComponentService componentService;
    private ComponentBuilder componentBuilder;
    private ComponentDTO componentDTO;

    @BeforeEach
    void setUp() {
        componentBuilder = new ComponentBuilder.Builder()
                .setName("name")
                .setDescription("description")
                .build();

        componentDTO = new ComponentDTO();
        componentDTO.setName("name");
        componentDTO.setDescription("description");
    }

    @Test
    void getAllComponents() throws SQLException, IOException {
        List<ComponentBuilder> components = new ArrayList<>();
        components.add(componentBuilder);

        when(componentDAO.getAllComponents()).thenReturn(components);
        List<ComponentDTO> result = componentService.getAllComponentsAsDTO();

        assertEquals(1, result.size());
        assertEquals(componentDTO.getName(), result.get(0).getName());
    }

    @Test
    void getComponentById() throws SQLException {
        int id = 1;

        when(componentDAO.getComponentById(id)).thenReturn(componentBuilder);
        ComponentDTO result = componentService.getComponentById(id);

        assertEquals(componentDTO.getName(), result.getName());
        assertEquals(componentDTO.getDescription(), result.getDescription());
    }

    @Test
    void addComponent() throws SQLException, IOException {
        componentService.addComponent(componentDTO);
        verify(componentDAO, times(1)).addComponent(any());
    }

    @Test
    void updateComponent() throws SQLException, IOException {
        componentService.updateComponent(componentDTO);
        verify(componentDAO, times(1)).updateComponent(any());
    }

    @Test
    void deleteComponent() throws SQLException, IOException {
        int id = 1;
        componentService.deleteComponent(id);
        verify(componentDAO, times(1)).deleteComponent(id);
    }
}
