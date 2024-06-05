package dao;

import connection.DateBaseConnectionSingleton;
import model.PatientBuilder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private final Connection connection;

    public PatientDAO() throws SQLException, IOException, ClassNotFoundException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }
    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    // Метод для получения списка всех пациентов
    public List<PatientBuilder> getAllPatients() {
        List<PatientBuilder> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int patient_id = resultSet.getInt("patient_id");
                    String name = resultSet.getString("name");
                    Date birth_date = resultSet.getDate("birth_date");

                    PatientBuilder builder = new PatientBuilder.Builder()
                            .setPatientID(patient_id)
                            .setName(name)
                            .setBirth_date(birth_date)
                            .setMedicalRecord(new MedicalRecordDAO(connection).getMedicalRecordByPatientId(patient_id))
                            .build();

                    patients.add(builder);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException  e) {
            e.fillInStackTrace();
        }
        return patients;
    }

    // Метод для добавления нового пациента
    public int addPatient(PatientBuilder patient) {
        String sql = "INSERT INTO Patients (name, birth_date) VALUES (?, ?) returning patient_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, new java.sql.Date(patient.getBirth_date().getTime()));
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }

    // Метод для обновления информации о пациенте
    public void updatePatient(PatientBuilder patient) {
        String sql = "UPDATE Patients SET name=?, birth_date=? WHERE patient_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, new java.sql.Date(patient.getBirth_date().getTime()));
            preparedStatement.setInt(3, patient.getPatientID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    // Метод для удаления пациента по его идентификатору
    public void deletePatient(int patient_id) {
        String sql1 = "DELETE FROM MedicalRecords WHERE patient_id=?";
        String sql2 = "DELETE FROM Patients  WHERE patient_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)){
            preparedStatement.setInt(1, patient_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)){
            preparedStatement.setInt(1, patient_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public PatientBuilder getPatientById(int patient_id) {
        PatientBuilder patient = null;
        String sql = "SELECT * FROM Patients WHERE patient_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, patient_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    Date birth_date = resultSet.getDate("birth_date");
                    patient = new PatientBuilder.Builder()
                            .setPatientID(patient_id)
                            .setName(name)
                            .setBirth_date(birth_date)
                            .setMedicalRecord(new MedicalRecordDAO(connection).getMedicalRecordByPatientId(patient_id))
                            .build();
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }
}
