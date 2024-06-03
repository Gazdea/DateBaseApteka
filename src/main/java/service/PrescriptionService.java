package service;

import dao.PrescriptionDAO;
import dto.PrescriptionDTO;
import dto.PrescriptionMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionService {
    private  PrescriptionDAO prescriptionDAO = null;

    public PrescriptionService(PrescriptionDAO prescriptionDAO) {
        this.prescriptionDAO = prescriptionDAO;
    }

    public  List<PrescriptionDTO> getAllPrescriptionsAsDTO() throws SQLException, IOException {
        return prescriptionDAO.getAllPrescriptions().stream().map(PrescriptionMapper::toDTO).collect(Collectors.toList());
    }

    public  void addPrescription(PrescriptionDTO prescription) throws SQLException, IOException {
        prescriptionDAO.addPrescription(PrescriptionMapper.toEntity(prescription));
    }

    public  void updatePrescription(PrescriptionDTO prescription) throws SQLException, IOException {
        prescriptionDAO.updatePrescription(PrescriptionMapper.toEntity(prescription));
    }

    public  void deletePrescription(int id) throws SQLException, IOException {
        prescriptionDAO.deletePrescription(id);
    }

    public PrescriptionDTO getPrescriptionByID(int id) throws SQLException, IOException {
       return PrescriptionMapper.toDTO(prescriptionDAO.getPrescriptionByID(id));

    }
}
