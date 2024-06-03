package model;

import java.util.List;
import java.util.Objects;

public class MedicationBuilder {
    private final int medication_id;
    private final String name;
    private final String description;
    private final List<ComponentBuilder> components;


    public MedicationBuilder(Builder builder) {
        this.medication_id = builder.medication_id;
        this.name = builder.name;
        this.description = builder.description;
        this.components = builder.components;
    }

    @Override
    public String toString() {
        return "MedicationBuilder{" +
                "medication_id=" + medication_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", components=" + components +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationBuilder that = (MedicationBuilder) o;
        return medication_id == that.medication_id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medication_id, name, description);
    }

    public int getmedication_id() {
        return medication_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ComponentBuilder> getComponents() {
        return components;
    }

    public  static  class Builder {
        private int medication_id;
        private String name;
        private String description;
        private List<ComponentBuilder> components;

        public Builder() {
        }

        public Builder setComponents(List<ComponentBuilder> components) {
            this.components = components;
            return this;
        }

        public Builder setmMedication_id(int medication_id) {
            this.medication_id = medication_id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MedicationBuilder build() {
            return new MedicationBuilder(this);
        }
    }
}
