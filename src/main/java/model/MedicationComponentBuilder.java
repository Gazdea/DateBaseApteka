package model;

import java.util.Objects;

public class MedicationComponentBuilder {
    private final int medicationId;
    private final int componentId;


    private MedicationComponentBuilder(Builder builder) {
        this.medicationId = builder.medicationId;
        this.componentId = builder.componentId;
    }

    @Override
    public String toString() {
        return "MedicationComponentBuilder{" +
                "medicationId=" + medicationId +
                ", componentId=" + componentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationComponentBuilder that = (MedicationComponentBuilder) o;
        return medicationId == that.medicationId && componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationId, componentId);
    }

    public int getMedicationId() {
        return medicationId;
    }

    public int getComponentId() {
        return componentId;
    }

    public static class Builder {
        private int medicationId;
        private int componentId;

        public Builder() {
        }

        public Builder setMedicationId(int medicationId) {
            this.medicationId = medicationId;
            return this;
        }

        public Builder setComponentId(int componentId) {
            this.componentId = componentId;
            return this;
        }

        public MedicationComponentBuilder build() {
            return new MedicationComponentBuilder(this);
        }
    }



}
