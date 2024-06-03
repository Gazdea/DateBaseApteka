package service;

import dao.MedicalRecordDAO;
import dto.MedicalRecordDTO;
import dto.MedicalRecordMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalRecordService {
    private final MedicalRecordDAO medicalRecordDAO;

    public MedicalRecordService(MedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }

    public List<MedicalRecordDTO> getAllMedicalRecordAsDTO() throws SQLException, IOException {
        return medicalRecordDAO.getAllMedicalRecords().stream().map(MedicalRecordMapper::toDTO).collect(Collectors.toList());
    }

    public MedicalRecordDTO getMedicalRecordById(int id) {
        return MedicalRecordMapper.toDTO(medicalRecordDAO.getMedicalRecordById(id));
    }

    public void addMedicalRecord(MedicalRecordDTO medicalRecord) throws SQLException, IOException {
        medicalRecordDAO.addMedicalRecord(MedicalRecordMapper.toEntity(medicalRecord));
    }

    public void updateMedicalRecord(MedicalRecordDTO medicalRecord) throws SQLException, IOException {
        medicalRecordDAO.updateMedicalRecord(MedicalRecordMapper.toEntity(medicalRecord));
    }

    public void deleteMedicalRecord(int id) throws SQLException, IOException {
        medicalRecordDAO.deleteMedicalRecord(id);
    }
}
