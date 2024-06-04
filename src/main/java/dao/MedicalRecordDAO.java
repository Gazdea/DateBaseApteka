package dao;

import connection.DateBaseConnectionSingleton;
import model.MedicalRecordBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {
    private final Connection connection;

    public MedicalRecordDAO() throws SQLException, IOException, ClassNotFoundException {
        this.connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }
    public MedicalRecordDAO(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        this.connection = connection;
    }

    // Метод для получения списка всех медицинских записей
    public List<MedicalRecordBuilder> getAllMedicalRecords() {
        List<MedicalRecordBuilder> medicalRecords = new ArrayList<>();
        String sql = "SELECT * FROM MedicalRecords";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int record_id = resultSet.getInt("record_id");
                int patient_id = resultSet.getInt("patient_id");
                String record_details = resultSet.getString("record_details");

                MedicalRecordBuilder medicalRecord = new MedicalRecordBuilder.Builder()
                        .setRecord_id(record_id)
                        .setPatient_id(patient_id)
                        .setRecord_details(record_details)
                        .build();
                medicalRecords.add(medicalRecord);
            }
        } catch (SQLException  e){
            e.fillInStackTrace();
        }
        return medicalRecords;
    }

    // Метод для добавления новой медицинской записи
    public int addMedicalRecord(MedicalRecordBuilder medicalRecord) throws SQLException {
        String sql = "INSERT INTO MedicalRecords (patient_id, record_details) VALUES (?, ?) returning record_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, medicalRecord.getPatient_id());
            preparedStatement.setString(2, medicalRecord.getRecord_details());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new SQLException();
    }

    // Метод для обновления информации о медицинской записи
    public void updateMedicalRecord(MedicalRecordBuilder medicalRecord) {
        String sql = "UPDATE MedicalRecords SET patient_id=?, record_details=? WHERE record_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, medicalRecord.getPatient_id());
            preparedStatement.setString(2, medicalRecord.getRecord_details());
            preparedStatement.setInt(3, medicalRecord.getRecord_id());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для удаления медицинской записи по её идентификатору
    public void deleteMedicalRecord(int recordId) {
        String sql = "DELETE FROM MedicalRecords WHERE record_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, recordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public MedicalRecordBuilder getMedicalRecordById(int recordId) {
        MedicalRecordBuilder medicalRecord = null;
        String sql = "SELECT * FROM MedicalRecords WHERE record_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, recordId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {


                if (resultSet.next()) {
                    int record_id = resultSet.getInt("record_id");
                    int patient_id = resultSet.getInt("patient_id");
                    String record_details = resultSet.getString("record_details");
                    medicalRecord = new MedicalRecordBuilder.Builder()
                            .setRecord_id(record_id)
                            .setPatient_id(patient_id)
                            .setRecord_details(record_details)
                            .build();
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return medicalRecord;
    }

    public MedicalRecordBuilder getMedicalRecordByPatientId(int patient_id) {
        MedicalRecordBuilder medicalRecord = null;
        String sql = "SELECT * FROM MedicalRecords WHERE patient_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, patient_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int record_id = resultSet.getInt("record_id");
                    String record_details = resultSet.getString("record_details");
                    medicalRecord = new MedicalRecordBuilder.Builder()
                            .setRecord_id(record_id)
                            .setPatient_id(patient_id)
                            .setRecord_details(record_details)
                            .build();
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return medicalRecord;
    }

}
