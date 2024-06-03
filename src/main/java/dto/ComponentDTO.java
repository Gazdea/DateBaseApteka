package dto;

public class ComponentDTO {
    private int component_id;
    private String name;
    private String description;

    public ComponentDTO() {
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

    public ComponentDTO setComponent_id(int component_id) {
        this.component_id = component_id;
        return this;
    }

    public ComponentDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ComponentDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
