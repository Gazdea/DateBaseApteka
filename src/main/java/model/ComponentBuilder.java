package model;

import java.util.Objects;

public class ComponentBuilder {
    private final int component_id;
    private final String name;
    private final String description;

    public ComponentBuilder(Builder builder) {
        this.component_id = builder.component_id;
        this.name = builder.name;
        this.description = builder.description;
    }

    @Override
    public String toString() {
        return "ComponentBuilder{" +
                "component_id=" + component_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentBuilder that = (ComponentBuilder) o;
        return component_id == that.component_id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(component_id, name, description);
    }

    public int getComponent_id() {
        return component_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private int component_id;
        private String name;
        private String description;

        public Builder() {
        }

        public Builder setComponent_id(int component_id) {
            this.component_id = component_id;
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

        public ComponentBuilder build() {
            return new ComponentBuilder(this);
        }
    }
}
