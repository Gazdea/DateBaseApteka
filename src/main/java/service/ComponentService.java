package service;

import dao.ComponentDAO;
import dto.ComponentDTO;
import dto.ComponentMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentService {
    private final ComponentDAO componentDAO;

    public ComponentService(ComponentDAO componentDAO) {
        this.componentDAO = componentDAO;
    }

    public List<ComponentDTO> getAllComponentsAsDTO() throws SQLException, IOException {
        return componentDAO.getAllComponents().stream().map(ComponentMapper::toDTO).collect(Collectors.toList());
    }

    public ComponentDTO getComponentById(int id) throws SQLException {
        return ComponentMapper.toDTO(componentDAO.getComponentById(id));
    }

    public void addComponent(ComponentDTO component) throws SQLException, IOException {
        componentDAO.addComponent(ComponentMapper.toEntity(component));
    }

    public void updateComponent(ComponentDTO component) throws SQLException, IOException {
        componentDAO.updateComponent(ComponentMapper.toEntity(component));
    }

    public void deleteComponent(int id) throws SQLException, IOException {
        componentDAO.deleteComponent(id);
    }

}
