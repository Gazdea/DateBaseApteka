package dto;

import model.PrescriptionBuilder;

public class PrescriptionMapper {

    public static PrescriptionDTO toDTO(PrescriptionBuilder prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionID(prescription.getPrescriptionID());
        prescriptionDTO.setMedicationID(prescription.getMedicationID());
        prescriptionDTO.setPatientID(prescription.getPatientID());
        prescriptionDTO.setDosage(prescription.getDosage());
        prescriptionDTO.setDate_of_prescribed(prescription.getDate_of_prescribed());
        return prescriptionDTO;
    }

    public static PrescriptionBuilder toEntity(PrescriptionDTO prescriptionDTO) {
        return new PrescriptionBuilder.Builder()
                .setPrescriptionID(prescriptionDTO.getPrescriptionID())
                .setMedicationID(prescriptionDTO.getMedicationID())
                .setPatientID(prescriptionDTO.getPatientID())
                .setDosage(prescriptionDTO.getDosage())
                .setDate_of_prescribed(prescriptionDTO.getDate_of_prescribed())
                .build();
    }
}
