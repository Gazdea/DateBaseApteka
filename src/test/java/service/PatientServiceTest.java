package service;

import dao.PatientDAO;
import dto.MedicalRecordDTO;
import dto.PatientDTO;
import dto.PatientMapper;
import model.MedicalRecordBuilder;
import model.PatientBuilder;
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
class PatientServiceTest {
    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private PatientService patientService;
    private PatientBuilder patientBuilder;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        patientBuilder = new PatientBuilder.Builder()
                .setPatientID(1)
                .setName("John Doe")
                .setBirth_date(new Date(System.currentTimeMillis()))
                .setMedicalRecord(new MedicalRecordBuilder.Builder().setRecord_details("record details").setPatient_id(1).setRecord_id(1).build())
                .build();

        // Setting up test data
        patientDTO = new PatientDTO();
        patientDTO.setPatient_id(1);
        patientDTO.setName("John Doe");
        patientDTO.setBirth_date(new Date(System.currentTimeMillis()));
        patientDTO.setMedical_record(new MedicalRecordDTO().setRecord_details("record details").setPatient_id(1).setRecord_id(1));
    }

    @Test
    void getAllPatients() throws SQLException, IOException {
        List<PatientBuilder> patientBuilderList = new ArrayList<>();
        patientBuilderList.add(patientBuilder);

        // Setting up mocked behavior
        List<PatientDTO> expectedPatients = new ArrayList<>();
        expectedPatients.add(patientDTO);

        when(patientDAO.getAllPatients()).thenReturn(patientBuilderList);

        // Calling the method under test
        List<PatientDTO> actualPatients = patientService.getAllPatientsAsDTO();

        // Verifying the result
        assertEquals(expectedPatients.size(), actualPatients.size());
        assertEquals(expectedPatients.get(0).getPatient_id(), actualPatients.get(0).getPatient_id());
        assertEquals(expectedPatients.get(0).getName(), actualPatients.get(0).getName());
        assertEquals(expectedPatients.get(0).getBirth_date(), actualPatients.get(0).getBirth_date());
    }

    @Test
    void getPatientById() {
        int id = 1;

        // Setting up mocked behavior
        when(patientDAO.getPatientById(id)).thenReturn(patientBuilder);

        // Calling the method under test
        PatientDTO actualPatient = patientService.getPatientById(id);

        // Verifying the result
        assertEquals(patientDTO.getPatient_id(), actualPatient.getPatient_id());
        assertEquals(patientDTO.getName(), actualPatient.getName());
        assertEquals(patientDTO.getBirth_date(), actualPatient.getBirth_date());
    }

    @Test
    void addPatient() throws SQLException, IOException {
        // Calling the method under test
        patientService.addPatient(patientDTO);

        // Verifying that the DAO method was called
        verify(patientDAO, times(1)).addPatient(any());
    }

    @Test
    void updatePatient() throws SQLException, IOException {
        // Calling the method under test
        patientService.updatePatient(patientDTO);

        // Verifying that the DAO method was called
        verify(patientDAO, times(1)).updatePatient(any());
    }

    @Test
    void deletePatient() throws SQLException, IOException {
        int id = 1;

        // Calling the method under test
        patientService.deletePatient(id);

        // Verifying that the DAO method was called
        verify(patientDAO, times(1)).deletePatient(id);
    }
}