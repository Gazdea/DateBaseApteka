package service;

import dao.PrescriptionDAO;
import dto.PrescriptionDTO;
import dto.PrescriptionMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionService {
    private final PrescriptionDAO prescriptionDAO;

    public PrescriptionService(PrescriptionDAO prescriptionDAO) {
        this.prescriptionDAO = prescriptionDAO;
    }

    public  List<PrescriptionDTO> getAllPrescriptionsAsDTO() throws SQLException, IOException {
        return prescriptionDAO.getAllPrescriptions().stream().map(PrescriptionMapper::toDTO).collect(Collectors.toList());
    }

    public  void addPrescription(PrescriptionDTO prescription) throws SQLException {
        prescriptionDAO.addPrescription(PrescriptionMapper.toEntity(prescription));
    }

    public  void updatePrescription(PrescriptionDTO prescription) throws SQLException {
        prescriptionDAO.updatePrescription(PrescriptionMapper.toEntity(prescription));
    }

    public  void deletePrescription(int id) throws SQLException {
        prescriptionDAO.deletePrescription(id);
    }

    public PrescriptionDTO getPrescriptionByID(int id) {
       return PrescriptionMapper.toDTO(prescriptionDAO.getPrescriptionByID(id));

    }
}
