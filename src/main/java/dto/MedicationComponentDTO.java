package dto;

public class MedicationComponentDTO {
    private int medicationId;
    private int componentId;

    public MedicationComponentDTO(int medicationId, int componentId) {
        this.medicationId = medicationId;
        this.componentId = componentId;
    }

    public MedicationComponentDTO() {

    }

    public int getMedicationId() {
        return medicationId;
    }

    public MedicationComponentDTO setMedicationId(int medicationId) {
        this.medicationId = medicationId;
        return this;
    }

    public int getComponentId() {
        return componentId;
    }

    public MedicationComponentDTO setComponentId(int componentId) {
        this.componentId = componentId;
        return this;
    }
}
