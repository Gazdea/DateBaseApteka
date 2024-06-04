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
    private Connection connection = null;

    public  ComponentDAO() {
        try {
            connection = DateBaseConnectionSingleton.getInstance().openConnection();
        } catch (SQLException | IOException|  ClassNotFoundException e)
        {
            e.fillInStackTrace();
        }
    }

    public ComponentDAO(Connection connection) {
        this.connection = connection;
    }


    // Метод для получения списка всех компонентов
    public List<ComponentBuilder> getAllComponents() {
        List<ComponentBuilder> components = new ArrayList<>();
        String sql = "SELECT * FROM components";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
            e.fillInStackTrace();
        }
        return components;
    }


    // Метод для добавления нового компонента
    public int addComponent(ComponentBuilder component) throws SQLException {
        String sql = "INSERT INTO Components (name, description) VALUES (?, ?) returning component_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getDescription());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
        throw new SQLException();
    }

    // Метод для обновления информации о компоненте
    public void updateComponent(ComponentBuilder component) {
        String sql = "UPDATE Components SET name=?, description=? WHERE component_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getDescription());
            preparedStatement.setInt(3, component.getComponent_id());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для удаления компонента по его идентификатору
    public void deleteComponent(int component_id) {
        String sql = "DELETE FROM Components WHERE component_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, component_id);
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.fillInStackTrace();
        }
    }

    public ComponentBuilder getComponentById(int id) {
        ComponentBuilder component = null;
        String sql = "SELECT * FROM Components WHERE component_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException  e) {
            throw new RuntimeException(e);
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
            try (ResultSet resultSet = preparedStatement.executeQuery()){
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
