package service;

import dao.MedicationComponentDAO;
import dto.MedicationComponentDTO;
import dto.MedicationComponentMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MedicationComponentService {
    private final MedicationComponentDAO medicationComponentDAO;

    public  MedicationComponentService(MedicationComponentDAO medicationComponentDAO) throws SQLException, IOException {
        this.medicationComponentDAO = medicationComponentDAO;
    }

    public List<MedicationComponentDTO> getAllMedicationComponentAsDTO() {
        return medicationComponentDAO.getAllMedicationComponents().stream().map(MedicationComponentMapper::toDTO).collect(Collectors.toList());
    }

    public void addMedicationComponent(MedicationComponentDTO medicationComponent) throws SQLException, IOException {
        medicationComponentDAO.addMedicationComponent(MedicationComponentMapper.toEntity(medicationComponent));
    }

    public void deleteMedicationComponent(int medicationid, int componentid) throws SQLException, IOException {
        medicationComponentDAO.deleteMedicationComponent(medicationid, componentid);
    }

}
