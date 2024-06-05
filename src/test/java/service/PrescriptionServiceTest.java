package service;

import dao.PrescriptionDAO;
import dto.PrescriptionDTO;
import dto.PrescriptionMapper;
import model.PrescriptionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {
    @Mock
    private PrescriptionDAO prescriptionDAO;

    @InjectMocks
    private PrescriptionService prescriptionService;
    private PrescriptionBuilder prescriptionBuilder;
    private PrescriptionDTO prescriptionDTO;

    @BeforeEach
    void setUp() {
        prescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPrescriptionID(1)
                .setPatientID(1)
                .setMedicationID(1)
                .setDate_of_prescribed(new Date(System.currentTimeMillis()))
                .setDosage("2 pills per day")
                .build();

        // Setting up test data
        prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionID(1);
        prescriptionDTO.setPatientID(1);
        prescriptionDTO.setMedicationID(1);
        prescriptionDTO.setDate_of_prescribed(new Date(System.currentTimeMillis()));
        prescriptionDTO.setDosage("2 pills per day");
    }

    @Test
    void getAllPrescriptions() throws SQLException, IOException {
        List<PrescriptionBuilder> prescriptionBuilderList = new ArrayList<>();
        prescriptionBuilderList.add(prescriptionBuilder);
        // Setting up mocked behavior
        List<PrescriptionDTO> expectedPrescriptions = new ArrayList<>();
        expectedPrescriptions.add(prescriptionDTO);

        when(prescriptionDAO.getAllPrescriptions()).thenReturn(prescriptionBuilderList);

        // Calling the method under test
        List<PrescriptionDTO> actualPrescriptions = prescriptionService.getAllPrescriptionsAsDTO();

        // Verifying the result
        assertEquals(expectedPrescriptions.size(), actualPrescriptions.size());
        assertEquals(expectedPrescriptions.get(0).getPrescriptionID(), actualPrescriptions.get(0).getPrescriptionID());
        assertEquals(expectedPrescriptions.get(0).getPatientID(), actualPrescriptions.get(0).getPatientID());
        assertEquals(expectedPrescriptions.get(0).getMedicationID(), actualPrescriptions.get(0).getMedicationID());
        assertEquals(expectedPrescriptions.get(0).getDate_of_prescribed(), actualPrescriptions.get(0).getDate_of_prescribed());
        assertEquals(expectedPrescriptions.get(0).getDosage(), actualPrescriptions.get(0).getDosage());
    }

    @Test
    void addPrescription() throws SQLException {
        // Calling the method under test
        prescriptionService.addPrescription(prescriptionDTO);

        // Verifying that the DAO method was called
        verify(prescriptionDAO, times(1)).addPrescription(any());
    }

    @Test
    void updatePrescription() throws SQLException {
        // Calling the method under test
        prescriptionService.updatePrescription(prescriptionDTO);

        // Verifying that the DAO method was called
        verify(prescriptionDAO, times(1)).updatePrescription(any());
    }

    @Test
    void deletePrescription() throws SQLException {
        int id = 1;

        // Calling the method under test
        prescriptionService.deletePrescription(id);

        // Verifying that the DAO method was called
        verify(prescriptionDAO, times(1)).deletePrescription(id);
    }

    @Test
    void getPrescriptionByID() {
        int id = 1;

        // Setting up mocked behavior
        when(prescriptionDAO.getPrescriptionByID(id)).thenReturn(prescriptionBuilder);

        // Calling the method under test
        PrescriptionDTO actualPrescription = prescriptionService.getPrescriptionByID(id);

        // Verifying the result
        assertEquals(prescriptionDTO.getPrescriptionID(), actualPrescription.getPrescriptionID());
        assertEquals(prescriptionDTO.getPatientID(), actualPrescription.getPatientID());
        assertEquals(prescriptionDTO.getMedicationID(), actualPrescription.getMedicationID());
        assertEquals(prescriptionDTO.getDate_of_prescribed(), actualPrescription.getDate_of_prescribed());
        assertEquals(prescriptionDTO.getDosage(), actualPrescription.getDosage());
    }
}