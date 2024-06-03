package service;

import dao.PatientDAO;
import dto.PatientDTO;
import dto.PatientMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PatientService {
    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public List<PatientDTO> getAllPatientsAsDTO() throws SQLException, IOException {
        return patientDAO.getAllPatients().stream().map(PatientMapper::toDTO).collect(Collectors.toList());
    }

    public PatientDTO getPatientById(int id) {
        return PatientMapper.toDTO(patientDAO.getPatientById(id));
    }

    public void addPatient(PatientDTO patient) throws SQLException, IOException {
        patientDAO.addPatient(PatientMapper.toEntity(patient));
    }

    public void updatePatient(PatientDTO patient) throws SQLException, IOException {
        patientDAO.updatePatient(PatientMapper.toEntity(patient));
    }

    public void deletePatient(int id) throws SQLException, IOException {
        patientDAO.deletePatient(id);
    }

}
