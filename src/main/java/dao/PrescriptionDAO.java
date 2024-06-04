package dao;

import connection.DateBaseConnectionSingleton;
import model.PrescriptionBuilder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {
    private final Connection connection;

    public  PrescriptionDAO() throws SQLException, IOException, ClassNotFoundException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех рецептов
    public List<PrescriptionBuilder> getAllPrescriptions() {
        List<PrescriptionBuilder> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM Prescriptions";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int prescription_id = resultSet.getInt("prescription_id");
                int patient_id = resultSet.getInt("patient_id");
                int medication_id = resultSet.getInt("medication_id");
                Date date_prescribed = resultSet.getDate("date_prescribed");
                String dosage = resultSet.getString("dosage");

                PrescriptionBuilder prescription = new PrescriptionBuilder.Builder()
                        .setPrescriptionID(prescription_id)
                        .setPatientID(patient_id)
                        .setMedicationID(medication_id)
                        .setDate_of_prescribed(date_prescribed)
                        .setDosage(dosage)
                        .build();

                prescriptions.add(prescription);
            }
        } catch (SQLException e){
            e.fillInStackTrace();
        }
        return prescriptions;
    }

    // Метод для добавления нового рецепта
    public void addPrescription(PrescriptionBuilder prescription) {
        String sql = "INSERT INTO Prescriptions (patient_id, medication_id, date_prescribed, dosage) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, prescription.getPatientID());
            preparedStatement.setInt(2, prescription.getMedicationID());
            preparedStatement.setDate(3, new java.sql.Date(prescription.getDate_of_prescribed().getTime()));
            preparedStatement.setString(4, prescription.getDosage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    // Метод для обновления информации о рецепте
    public void updatePrescription(PrescriptionBuilder prescription) {
        String sql = "UPDATE Prescriptions SET patient_id=?, medication_id=?, date_prescribed=?, dosage=? WHERE prescription_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, prescription.getPatientID());
            preparedStatement.setInt(2, prescription.getMedicationID());
            preparedStatement.setDate(3, new java.sql.Date(prescription.getDate_of_prescribed().getTime()));
            preparedStatement.setString(4, prescription.getDosage());
            preparedStatement.setInt(5, prescription.getPrescriptionID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    // Метод для удаления рецепта по его идентификатору
    public void deletePrescription(int id) {
        String sql = "DELETE FROM Prescriptions WHERE prescription_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public PrescriptionBuilder getPrescriptionByID(int id) {
        PrescriptionBuilder prescription = null;
        String sql = "SELECT * FROM Prescriptions WHERE prescription_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            if (resultSet.next()) {
                int prescription_id = resultSet.getInt("prescription_id");
                int patient_id = resultSet.getInt("patient_id");
                int medication_id = resultSet.getInt("medication_id");
                Date date_prescribed = resultSet.getDate("date_prescribed");
                String dosage = resultSet.getString("dosage");
                prescription = new PrescriptionBuilder.Builder()
                        .setPrescriptionID(prescription_id)
                        .setPatientID(patient_id)
                        .setMedicationID(medication_id)
                        .setDate_of_prescribed(date_prescribed)
                        .setDosage(dosage)
                        .build();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return prescription;
    }
}
