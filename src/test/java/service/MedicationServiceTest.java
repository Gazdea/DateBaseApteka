package service;

import dao.MedicationDAO;
import dto.ComponentDTO;
import dto.MedicationDTO;
import dto.MedicationMapper;
import model.ComponentBuilder;
import model.MedicationBuilder;
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
class MedicationServiceTest {
    @Mock
    private MedicationDAO medicationDAO;

    @InjectMocks
    private MedicationService medicationService;
    private MedicationBuilder medicationBuilder;
    private MedicationDTO medicationDTO;

    @BeforeEach
    void setUp() {
        List<ComponentBuilder> componentsbuilder = new ArrayList<>();
        componentsbuilder.add(new ComponentBuilder.Builder().setName("Component 1").setDescription("Test Component 1").build());

        medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("Test Description")
                .setName("Test Medication")
                .setmMedication_id(1)
                .setComponents(componentsbuilder)
                .build();

        List<ComponentDTO> componentsdto = new ArrayList<>();
        componentsdto.add(new ComponentDTO().setName("Component 1").setDescription("Test Component 1"));

        // Setting up test data
        medicationDTO = new MedicationDTO();
        medicationDTO.setMedication_id(1);
        medicationDTO.setName("Test Medication");
        medicationDTO.setDescription("Test Description");
        medicationDTO.setComponents(componentsdto);
    }

    @Test
    void getAllMedications() throws SQLException, IOException {
        List<MedicationBuilder> medicationBuilders = new ArrayList<>();
        medicationBuilders.add(medicationBuilder);
        // Setting up mocked behavior
        List<MedicationDTO> expectedMedications = new ArrayList<>();
        expectedMedications.add(medicationDTO);

        when(medicationDAO.getAllMedicationBuilders()).thenReturn(medicationBuilders);

        // Calling the method under test
        List<MedicationDTO> actualMedications = medicationService.getAllMedicationAsDTO();

        // Verifying the result
        assertEquals(expectedMedications.size(), actualMedications.size());
        assertEquals(expectedMedications.get(0).getMedication_id(), actualMedications.get(0).getMedication_id());
        assertEquals(expectedMedications.get(0).getName(), actualMedications.get(0).getName());
        assertEquals(expectedMedications.get(0).getDescription(), actualMedications.get(0).getDescription());
    }

    @Test
    void addMedication() throws SQLException, IOException {
        // Calling the method under test
        medicationService.addMedication(medicationDTO);

        // Verifying that the DAO method was called
        verify(medicationDAO, times(1)).addMedicationBuilder(any());
    }

    @Test
    void updateMedication() {
        // Calling the method under test
        medicationService.updateMedication(medicationDTO);

        // Verifying that the DAO method was called
        verify(medicationDAO, times(1)).updateMedicationBuilder(any());
    }

    @Test
    void deleteMedication() {
        int medicationId = 1;

        // Calling the method under test
        medicationService.deleteMedication(medicationId);

        // Verifying that the DAO method was called
        verify(medicationDAO, times(1)).deleteMedicationBuilder(medicationId);
    }

    @Test
    void getMedicationById() {
        int medicationId = 1;

        // Setting up mocked behavior
        when(medicationDAO.getMedicationById(medicationId)).thenReturn(medicationBuilder);

        // Calling the method under test
        MedicationDTO actualMedication = medicationService.getMedicationById(medicationId);

        // Verifying the result
        assertEquals(medicationDTO.getMedication_id(), actualMedication.getMedication_id());
        assertEquals(medicationDTO.getName(), actualMedication.getName());
        assertEquals(medicationDTO.getDescription(), actualMedication.getDescription());
    }
}