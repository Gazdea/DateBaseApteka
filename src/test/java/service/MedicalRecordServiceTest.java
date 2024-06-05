package service;

import junit.framework.TestCase;

import dao.MedicalRecordDAO;
import dto.MedicalRecordDTO;
import model.MedicalRecordBuilder;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock
    private MedicalRecordDAO medicalRecordDAO;

    @InjectMocks
    private MedicalRecordService medicalRecordService;
    private MedicalRecordBuilder medicalRecordBuilder;
    private MedicalRecordDTO medicalRecordDTO;

    @BeforeEach
    public void setUp() {
        // Setting up test data
        medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setRecord_id(1);
        medicalRecordDTO.setPatient_id(123);
        medicalRecordDTO.setRecord_details("Test record details");

        medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_id(1)
                .setPatient_id(123)
                .setRecord_details("Test record details")
                .build();
    }

    @Test
    void getAllMedicalRecords() throws SQLException, IOException {
        List<MedicalRecordBuilder> medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecordBuilder);

        List<MedicalRecordDTO> expectedMedicalRecords = new ArrayList<>();
        expectedMedicalRecords.add(medicalRecordDTO);

        when(medicalRecordDAO.getAllMedicalRecords()).thenReturn(medicalRecords);

        List<MedicalRecordDTO> actualMedicalRecords = medicalRecordService.getAllMedicalRecordAsDTO();

        assertEquals(expectedMedicalRecords.size(), actualMedicalRecords.size());
        assertEquals(expectedMedicalRecords.get(0).getRecord_id(), actualMedicalRecords.get(0).getRecord_id());
        assertEquals(expectedMedicalRecords.get(0).getPatient_id(), actualMedicalRecords.get(0).getPatient_id());
        assertEquals(expectedMedicalRecords.get(0).getRecord_details(), actualMedicalRecords.get(0).getRecord_details());
    }

    @Test
    void getMedicalRecordById() throws SQLException {

        // Setting up mocked behavior
        int id = 1;
        when(medicalRecordDAO.getMedicalRecordById(id)).thenReturn(medicalRecordBuilder);

        // Calling the method under test
        MedicalRecordDTO actualMedicalRecord = medicalRecordService.getMedicalRecordById(id);

        // Verifying the result
        assertEquals(medicalRecordDTO.getRecord_id(), actualMedicalRecord.getRecord_id());
        assertEquals(medicalRecordDTO.getPatient_id(), actualMedicalRecord.getPatient_id());
        assertEquals(medicalRecordDTO.getRecord_details(), actualMedicalRecord.getRecord_details());
    }

    @Test
    void addMedicalRecord() throws SQLException, IOException {
        // Calling the method under test
        medicalRecordService.addMedicalRecord(medicalRecordDTO);

        // Verifying that the DAO method was called
        verify(medicalRecordDAO, times(1)).addMedicalRecord(any());
    }

    @Test
    void updateMedicalRecord() throws SQLException, IOException {
        // Calling the method under test
        medicalRecordService.updateMedicalRecord(medicalRecordDTO);

        // Verifying that the DAO method was called
        verify(medicalRecordDAO, times(1)).updateMedicalRecord(any());
    }

    @Test
    void deleteMedicalRecord() throws SQLException, IOException {
        int id = 1;

        // Calling the method under test
        medicalRecordService.deleteMedicalRecord(id);

        // Verifying that the DAO method was called
        verify(medicalRecordDAO, times(1)).deleteMedicalRecord(id);
    }
}