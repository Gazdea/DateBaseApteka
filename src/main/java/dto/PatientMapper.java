package dto;

import model.PatientBuilder;

public class PatientMapper {

    public static PatientDTO toDTO(PatientBuilder patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatient_id(patient.getPatientID());
        patientDTO.setName(patient.getName());
        patientDTO.setBirth_date(patient.getBirth_date());
        if(patient.getMedicalRecord() != null)
        {
           patientDTO.setMedical_record(MedicalRecordMapper.toDTO(patient.getMedicalRecord()));
        }

        return patientDTO;
    }

    public static PatientBuilder toEntity(PatientDTO patientDTO) {
        return new PatientBuilder.Builder()
                .setPatientID(patientDTO.getPatient_id())
                .setName(patientDTO.getName())
                .setBirth_date(patientDTO.getBirth_date())
                .setMedicalRecord(patientDTO.getMedical_record() != null ? MedicalRecordMapper.toEntity(patientDTO.getMedical_record()) : null)
                .build();

    }
}
