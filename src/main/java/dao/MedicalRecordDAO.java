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
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public MedicalRecordDAO() throws SQLException, IOException {
        connection = DateBaseConnectionSingleton.getInstance().openConnection();
    }

    // Метод для получения списка всех медицинских записей
    public List<MedicalRecordBuilder> getAllMedicalRecords() throws SQLException, IOException {
        List<MedicalRecordBuilder> medicalRecords = new ArrayList<>();

        try {
            String sql = "SELECT * FROM MedicalRecords";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

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
            e.printStackTrace();
        }
        return medicalRecords;
    }

    // Метод для добавления новой медицинской записи
    public void addMedicalRecord(MedicalRecordBuilder medicalRecord) {
        try {
            String sql = "INSERT INTO MedicalRecords (patient_id, record_details) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, medicalRecord.getPatient_id());
            preparedStatement.setString(2, medicalRecord.getRecord_details());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления информации о медицинской записи
    public void updateMedicalRecord(MedicalRecordBuilder medicalRecord) {
        try {
            String sql = "UPDATE MedicalRecords SET patient_id=?, record_details=? WHERE record_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, medicalRecord.getPatient_id());
            preparedStatement.setString(2, medicalRecord.getRecord_details());
            preparedStatement.setInt(3, medicalRecord.getRecord_id());
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления медицинской записи по её идентификатору
    public void deleteMedicalRecord(int recordId) {
        try {
            String sql = "DELETE FROM MedicalRecords WHERE record_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MedicalRecordBuilder getMedicalRecordById(int recordId) {
        MedicalRecordBuilder medicalRecord = null;
        try {
            String sql = "SELECT * FROM MedicalRecords WHERE record_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recordId);
            resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }

    public MedicalRecordBuilder getMedicalRecordByPatientId(int patient_id) {
        MedicalRecordBuilder medicalRecord = null;
        String sql = "SELECT * FROM MedicalRecords WHERE patient_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, patient_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int record_id = resultSet.getInt("record_id");
                String record_details = resultSet.getString("record_details");
                medicalRecord = new MedicalRecordBuilder.Builder()
                        .setRecord_id(record_id)
                        .setPatient_id(patient_id)
                        .setRecord_details(record_details)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }

}
