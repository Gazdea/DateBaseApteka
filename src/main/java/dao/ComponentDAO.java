package dao;

import connection.DateBaseConnectionSingleton;
import model.ComponentBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentDAO {
    private Connection connection;
    private  PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public  ComponentDAO() throws SQLException, IOException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех компонентов
    public List<ComponentBuilder> getAllComponents() {
        List<ComponentBuilder> components = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Components");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int component_id = resultSet.getInt("component_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                ComponentBuilder component = new ComponentBuilder.Builder()
                        .setComponent_id(component_id)
                        .setName(name)
                        .setDescription(description)
                        .build();

                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }


    // Метод для добавления нового компонента
    public void addComponent(ComponentBuilder component) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO Components (name, description) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления информации о компоненте
    public void updateComponent(ComponentBuilder component) {
        try {
            String sql = "UPDATE Components SET name=?, description=? WHERE component_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getDescription());
            preparedStatement.setInt(3, component.getComponent_id());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления компонента по его идентификатору
    public void deleteComponent(int component_id) {
        try {
            String sql = "DELETE FROM Components WHERE component_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, component_id);
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public ComponentBuilder getComponentById(int id) {
        ComponentBuilder component = null;
        try {
            String sql = "SELECT * FROM Components WHERE component_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int component_id = resultSet.getInt("component_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                component = new ComponentBuilder.Builder()
                    .setComponent_id(component_id)
                    .setName(name)
                    .setDescription(description)
                    .build();
            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return component;
    }

    // Метод для получения списка всех компонентов
    public List<ComponentBuilder> getComponentByMedicamentId(int id) {
        List<ComponentBuilder> components = new ArrayList<>();
        String sql = "SELECT Components.component_id, Components.name, Components.description " +
                "FROM Components " +
                "JOIN medicationcomponents ON Components.component_id = medicationcomponents.component_id " +
                "WHERE medicationcomponents.medication_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int component_id = resultSet.getInt("component_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");

                    ComponentBuilder component = new ComponentBuilder.Builder()
                            .setComponent_id(component_id)
                            .setName(name)
                            .setDescription(description)
                            .build();

                    components.add(component);
                }
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return components;
    }
}