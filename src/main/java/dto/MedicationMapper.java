package dto;

import model.MedicationBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class MedicationMapper {

    public static MedicationDTO toDTO(MedicationBuilder medicationBuilder) {
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setMedication_id(medicationBuilder.getmedication_id());
        medicationDTO.setName(medicationBuilder.getName());
        medicationDTO.setDescription(medicationBuilder.getDescription());
        if(medicationBuilder.getComponents() != null)
        {
            List<ComponentDTO> componentDTOS = medicationBuilder.getComponents().stream()
                    .map(ComponentMapper::toDTO)
                    .collect(Collectors.toList());
            medicationDTO.setComponents(componentDTOS);
        }
        return medicationDTO;
    }

    public static MedicationBuilder toEntity(MedicationDTO medicationDTO) {
        return new MedicationBuilder.Builder()
            .setmMedication_id(medicationDTO.getMedication_id())
            .setName(medicationDTO.getName())
            .setDescription(medicationDTO.getDescription())
            .build();
    }
}
