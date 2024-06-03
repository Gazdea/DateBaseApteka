package dto;

import model.MedicationComponentBuilder;

public class MedicationComponentMapper {

    public static MedicationComponentDTO toDTO(MedicationComponentBuilder medicationComponent) {
        MedicationComponentDTO medicationComponentDTO = new MedicationComponentDTO();
        medicationComponentDTO.setComponentId(medicationComponent.getComponentId());
        medicationComponentDTO.setMedicationId(medicationComponent.getMedicationId());
        return medicationComponentDTO;
    }

    public static MedicationComponentBuilder toEntity(MedicationComponentDTO medicationComponent) {
        return new MedicationComponentBuilder.Builder()
            .setComponentId(medicationComponent.getComponentId())
            .setMedicationId(medicationComponent.getMedicationId())
            .build();
    }


}
