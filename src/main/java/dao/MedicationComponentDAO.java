package dao;

import connection.DateBaseConnectionSingleton;
import model.MedicationComponentBuilder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationComponentDAO {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public MedicationComponentDAO() throws SQLException, IOException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех компонентов медикаментов
    public List<MedicationComponentBuilder> getAllMedicationComponents() {
        List<MedicationComponentBuilder> medicationComponents = new ArrayList<>();

        try {
            String sql = "SELECT * FROM MedicationComponents";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int medication_id = resultSet.getInt("medication_id");
                int component_id = resultSet.getInt("component_id");

                MedicationComponentBuilder component = new MedicationComponentBuilder.Builder()
                        .setMedicationId(medication_id)
                        .setComponentId(component_id)
                        .build();

                medicationComponents.add(component);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return medicationComponents;
    }

    // Метод для добавления нового компонента медикамента
    public void addMedicationComponent(MedicationComponentBuilder medicationComponent) {
        String sql = "INSERT INTO MedicationComponents (medication_id, component_id) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, medicationComponent.getMedicationId());
            preparedStatement.setInt(2, medicationComponent.getComponentId());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.fillInStackTrace();
        }
    }

    // Метод для удаления компонента медикамента по его идентификатору
    public void deleteMedicationComponent(int medicationId, int componentId) {
        String sql = "DELETE FROM MedicationComponents WHERE medication_id=? AND component_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, medicationId);
            preparedStatement.setInt(2, componentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
}
