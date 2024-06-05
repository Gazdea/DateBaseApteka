package service;

import dao.MedicationComponentDAO;
import dto.MedicationComponentDTO;
import dto.MedicationComponentMapper;
import model.ComponentBuilder;
import model.MedicationComponentBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationComponentServiceTest {
    @Mock
    private MedicationComponentDAO medicationComponentDAO;

    @InjectMocks
    private MedicationComponentService medicationComponentService;
    private MedicationComponentBuilder medicationComponentBuilder;
    private MedicationComponentDTO medicationComponentDTO;

    @BeforeEach
    void setUp() {
        medicationComponentBuilder = new MedicationComponentBuilder.Builder()
                .setMedicationId(1)
                .setComponentId(2)
                .build();
        // Setting up test data
        medicationComponentDTO = new MedicationComponentDTO();
        medicationComponentDTO.setMedicationId(1);
        medicationComponentDTO.setComponentId(2);
    }

    @Test
    void getAllMedicationComponents() {
        List<MedicationComponentBuilder> medicationComponentBuilderList = new ArrayList<>();
        medicationComponentBuilderList.add(medicationComponentBuilder);

        // Setting up mocked behavior
        List<MedicationComponentDTO> expectedMedicationComponents = new ArrayList<>();
        expectedMedicationComponents.add(medicationComponentDTO);

        when(medicationComponentDAO.getAllMedicationComponents()).thenReturn(medicationComponentBuilderList);

        // Calling the method under test
        List<MedicationComponentDTO> actualMedicationComponents = medicationComponentService.getAllMedicationComponentAsDTO();

        // Verifying the result
        assertEquals(expectedMedicationComponents.size(), actualMedicationComponents.size());
        assertEquals(expectedMedicationComponents.get(0).getMedicationId(), actualMedicationComponents.get(0).getMedicationId());
        assertEquals(expectedMedicationComponents.get(0).getComponentId(), actualMedicationComponents.get(0).getComponentId());
    }

    @Test
    void addMedicationComponent() throws SQLException, IOException {
        // Calling the method under test
        medicationComponentService.addMedicationComponent(medicationComponentDTO);

        // Verifying that the DAO method was called
        verify(medicationComponentDAO, times(1)).addMedicationComponent(any());
    }

    @Test
    void deleteMedicationComponent() throws SQLException, IOException {
        int medicationId = 1;
        int componentId = 2;

        // Calling the method under test
        medicationComponentService.deleteMedicationComponent(medicationId, componentId);

        // Verifying that the DAO method was called
        verify(medicationComponentDAO, times(1)).deleteMedicationComponent(medicationId, componentId);
    }
}