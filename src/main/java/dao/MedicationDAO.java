package dao;

import connection.DateBaseConnectionSingleton;
import model.MedicationBuilder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {
    private Connection  connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ComponentDAO componentDAO;

    public MedicationDAO() throws SQLException, IOException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех медикаментов
    public List<MedicationBuilder> getAllMedicationBuilders() throws SQLException, IOException {
        List<MedicationBuilder> medicationBuilders = new ArrayList<>();
        String sql = "SELECT medication_id, name, description FROM Medications";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int medication_id = resultSet.getInt("medication_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                MedicationBuilder builder = new MedicationBuilder.Builder()
                        .setmMedication_id(medication_id)
                        .setName(name)
                        .setDescription(description)
                        .setComponents(new ComponentDAO().getComponentByMedicamentId(medication_id))
                        .build();

                medicationBuilders.add(builder);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return medicationBuilders;
    }

    // Метод для добавления нового медикамента
    public void addMedicationBuilder(MedicationBuilder MedicationBuilder) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO Medications (name, description) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, MedicationBuilder.getName());
            preparedStatement.setString(2, MedicationBuilder.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления информации о медикаменте
    public void updateMedicationBuilder(MedicationBuilder MedicationBuilder) {

        try {
            String sql = "UPDATE Medications SET name=?, description=? WHERE medication_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, MedicationBuilder.getName());
            preparedStatement.setString(2, MedicationBuilder.getDescription());
            preparedStatement.setInt(3, MedicationBuilder.getmedication_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления медикамента по его идентификатору
    public void deleteMedicationBuilder(int id) {
        try {
            String sql = "DELETE FROM Medications WHERE medication_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MedicationBuilder getMedicationById(int id) {
        MedicationBuilder medicationBuilder = null;
        try {
            String sql = "SELECT * FROM Medications WHERE medication_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                int medication_id = resultSet.getInt("medication_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                medicationBuilder = new MedicationBuilder.Builder()
                        .setmMedication_id(medication_id)
                        .setName(name)
                        .setDescription(description)
                        .setComponents(componentDAO.getComponentByMedicamentId(id))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicationBuilder;
    }

}
