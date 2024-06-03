package dto;

import model.MedicalRecordBuilder;

public class MedicalRecordMapper {

    public static MedicalRecordDTO toDTO(MedicalRecordBuilder medicalRecord) {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setPatient_id(medicalRecord.getPatient_id());
        medicalRecordDTO.setRecord_id(medicalRecord.getRecord_id());
        medicalRecordDTO.setRecord_details(medicalRecord.getRecord_details());
        return medicalRecordDTO;
    }

    public static MedicalRecordBuilder toEntity(MedicalRecordDTO medicalRecordDTO) {
        return new MedicalRecordBuilder.Builder()
        .setPatient_id(medicalRecordDTO.getPatient_id())
        .setRecord_id(medicalRecordDTO.getRecord_id())
        .setRecord_details(medicalRecordDTO.getRecord_details())
        .build();
    }
}
