package dto;

import model.ComponentBuilder;

public class ComponentMapper {

    public static ComponentDTO toDTO(ComponentBuilder componentBuilder) {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponent_id(componentBuilder.getComponent_id());
        componentDTO.setName(componentBuilder.getName());
        componentDTO.setDescription(componentBuilder.getDescription());
        return componentDTO;
    }

    public static ComponentBuilder toEntity(ComponentDTO componentDTO) {
        return new ComponentBuilder.Builder()
            .setComponent_id(componentDTO.getComponent_id())
            .setName(componentDTO.getName())
            .setDescription(componentDTO.getDescription())
            .build();
    }
}
