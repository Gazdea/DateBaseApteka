package dao;

import connection.DateBaseConnectionSingleton;
import model.PatientBuilder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public PatientDAO() throws SQLException, IOException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех пациентов
    public List<PatientBuilder> getAllPatients() {
        List<PatientBuilder> patients = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Patients";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int patient_id = resultSet.getInt("patient_id");
                String name = resultSet.getString("name");
                Date birth_date = resultSet.getDate("birth_date");

                PatientBuilder builder = new PatientBuilder.Builder()
                        .setPatientID(patient_id)
                        .setName(name)
                        .setBirth_date(birth_date)
                        .setMedicalRecord(new MedicalRecordDAO().getMedicalRecordByPatientId(patient_id))
                        .build();

                patients.add(builder);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Метод для добавления нового пациента
    public void addPatient(PatientBuilder patient) {
        try {
            String sql = "INSERT INTO Patients (name, birth_date) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, new java.sql.Date(patient.getBirth_date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления информации о пациенте
    public void updatePatient(PatientBuilder patient) {
        try {
            String sql = "UPDATE Patients SET name=?, birth_date=? WHERE patient_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, new java.sql.Date(patient.getBirth_date().getTime()));
            preparedStatement.setInt(3, patient.getPatientID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления пациента по его идентификатору
    public void deletePatient(int patient_id) {
        try {
            String sql = "DELETE FROM Patients WHERE patient_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, patient_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PatientBuilder getPatientById(int patient_id) {
        PatientBuilder patient = null;
        try {
            String sql = "SELECT * FROM Patients WHERE patient_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, patient_id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Date birth_date = resultSet.getDate("birth_date");
                patient = new PatientBuilder.Builder()
                        .setPatientID(patient_id)
                        .setName(name)
                        .setBirth_date(birth_date)
                        .setMedicalRecord(new MedicalRecordDAO().getMedicalRecordByPatientId(patient_id))
                        .build();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }
}
