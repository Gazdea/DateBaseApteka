package dto;

import java.util.ArrayList;
import java.util.List;

public class MedicationDTO {
    private int medication_id;
    private String name;
    private String description;
    private List<ComponentDTO> components;

    public MedicationDTO(int medication_id, String name, String description) {
        this.medication_id = medication_id;
        this.name = name;
        this.description = description;
        this.components = new ArrayList<ComponentDTO>();
    }

    public List<ComponentDTO> getComponents() {
        return components;
    }

    public MedicationDTO setComponents(List<ComponentDTO> components) {
        this.components = components;
        return this;
    }

    public MedicationDTO() {

    }

    public int getMedication_id() {
        return medication_id;
    }

    public MedicationDTO setMedication_id(int medication_id) {
        this.medication_id = medication_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedicationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MedicationDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
