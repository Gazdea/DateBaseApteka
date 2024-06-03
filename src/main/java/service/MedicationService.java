package service;

import dao.MedicationDAO;
import dto.MedicationDTO;
import dto.MedicationMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MedicationService {
    private final MedicationDAO medicationDAO;

    public MedicationService(MedicationDAO medicationDAO) {
        this.medicationDAO = medicationDAO;
    }

    public List<MedicationDTO> getAllMedicationAsDTO() throws SQLException, IOException {
        return medicationDAO.getAllMedicationBuilders().stream().map(MedicationMapper::toDTO).collect(Collectors.toList());
    }

    public void addMedication(MedicationDTO medication) throws SQLException, IOException {
        medicationDAO.addMedicationBuilder(MedicationMapper.toEntity(medication));
    }

    public void updateMedication(MedicationDTO medication) {
        medicationDAO.updateMedicationBuilder(MedicationMapper.toEntity(medication));
    }

    public void deleteMedication(int id) {
        medicationDAO.deleteMedicationBuilder(id);
    }

    public MedicationDTO getMedicationById(int id) {
        return MedicationMapper.toDTO(medicationDAO.getMedicationById(id));
    }

}
