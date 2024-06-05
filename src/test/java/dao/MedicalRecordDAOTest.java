package dao;

import model.ComponentBuilder;
import model.MedicalRecordBuilder;
import model.PatientBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MedicalRecordDAOTest {
    @Container
    private  static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    private MedicalRecordDAO medicalRecordDAO;
    private PatientDAO patientDAO;

    @BeforeAll
    public void beforeAll() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void setUp() throws SQLException, IOException, ClassNotFoundException {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Patients (\n" +
                    "    patient_id SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(100) NOT NULL,\n" +
                    "    birth_date DATE NOT NULL,\n" +
                    "    medical_record VARCHAR(255) UNIQUE -- Связь один-к-одному с таблицей MedicalRecords\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS MedicalRecords (\n" +
                    "    record_id SERIAL PRIMARY KEY,\n" +
                    "    patient_id INT UNIQUE,\n" +
                    "    record_details TEXT,\n" +
                    "    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Medications (\n" +
                    "    medication_id SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(100) NOT NULL,\n" +
                    "    description TEXT\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Prescriptions (\n" +
                    "    prescription_id SERIAL PRIMARY KEY,\n" +
                    "    patient_id INT,\n" +
                    "    medication_id INT,\n" +
                    "    date_prescribed DATE NOT NULL,\n" +
                    "    dosage VARCHAR(50),\n" +
                    "    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),\n" +
                    "    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Components (\n" +
                    "    component_id SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(100) NOT NULL,\n" +
                    "    description TEXT\n" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS MedicationComponents (\n" +
                    "    medication_id INT,\n" +
                    "    component_id INT,\n" +
                    "    PRIMARY KEY (medication_id, component_id),\n" +
                    "    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id),\n" +
                    "    FOREIGN KEY (component_id) REFERENCES Components(component_id)\n" +
                    ");");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        medicalRecordDAO = new MedicalRecordDAO(connection);
        patientDAO = new PatientDAO(connection);
    }

    @AfterAll
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testAddMedicalRecordAndGetById() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicalRecordBuilder medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_details("test")
                .setPatient_id(idpatient)
                .build();
        int idmedrecord = medicalRecordDAO.addMedicalRecord(medicalRecordBuilder);

        MedicalRecordBuilder medicalRecordFromBD = medicalRecordDAO.getMedicalRecordById(idmedrecord);

        assertNotNull(medicalRecordFromBD,"Should not be null");
        assertEquals(medicalRecordFromBD.getRecord_details(), medicalRecordBuilder.getRecord_details(),"Medical Record should match");
        assertEquals(medicalRecordFromBD.getPatient_id(), medicalRecordBuilder.getPatient_id(), "Description should match");
    }

    @Test
    void updateMedicalRecord() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicalRecordBuilder medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_details("test")
                .setPatient_id(idpatient)
                .build();
        int idmedrecord = medicalRecordDAO.addMedicalRecord(medicalRecordBuilder);

        MedicalRecordBuilder updateMedicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_id(idmedrecord)
                .setRecord_details("test2")
                .setPatient_id(idpatient)
                .build();

        medicalRecordDAO.updateMedicalRecord(updateMedicalRecordBuilder);

        MedicalRecordBuilder medicalRecordFromBD = medicalRecordDAO.getMedicalRecordById(idmedrecord);

        assertEquals(medicalRecordFromBD.getRecord_details(), updateMedicalRecordBuilder.getRecord_details(),"Medical Record should match");
        assertEquals(medicalRecordFromBD.getPatient_id(), updateMedicalRecordBuilder.getPatient_id(), "Description should match");

    }

    @Test
    void deleteMedicalRecord() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicalRecordBuilder medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_details("test")
                .setPatient_id(idpatient)
                .build();
        int idmedrecord = medicalRecordDAO.addMedicalRecord(medicalRecordBuilder);


        medicalRecordDAO.deleteMedicalRecord(idmedrecord);

        MedicalRecordBuilder medicalRecordFromBD = medicalRecordDAO.getMedicalRecordById(idmedrecord);
        assertNull(medicalRecordFromBD,"Should be null");
    }

    @Test
    void getAllMedicalRecord() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicalRecordBuilder medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_details("test")
                .setPatient_id(idpatient)
                .build();
        medicalRecordDAO.addMedicalRecord(medicalRecordBuilder);

        PatientBuilder patientBuilder2 = new PatientBuilder.Builder()
                .setName("test2")
                .setBirth_date(new Date(1234))
                .build();

        int idpatient2 = patientDAO.addPatient(patientBuilder2);

        MedicalRecordBuilder medicalRecordBuilder2 = new MedicalRecordBuilder.Builder()
                .setRecord_details("test2")
                .setPatient_id(idpatient2)
                .build();
        medicalRecordDAO.addMedicalRecord(medicalRecordBuilder2);

        List<MedicalRecordBuilder> medicalRecordFromBD = medicalRecordDAO.getAllMedicalRecords();

        assertNotNull(medicalRecordFromBD,"Should not be null");
        assertTrue(medicalRecordFromBD.stream().anyMatch(p-> p.getRecord_details().equals(medicalRecordBuilder.getRecord_details())), "Medical Record should match");

    }

    @Test
    void getMedicalRecordByPatientId() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicalRecordBuilder medicalRecordBuilder = new MedicalRecordBuilder.Builder()
                .setRecord_details("test")
                .setPatient_id(idpatient)
                .build();
        medicalRecordDAO.addMedicalRecord(medicalRecordBuilder);

        MedicalRecordBuilder medicalRecordFromBD = medicalRecordDAO.getMedicalRecordByPatientId(idpatient);

        assertNotNull(medicalRecordFromBD,"Should not be null");
        assertEquals(medicalRecordFromBD.getRecord_details(), medicalRecordBuilder.getRecord_details(),"Medical Record should match");
        assertEquals(medicalRecordFromBD.getPatient_id(), medicalRecordBuilder.getPatient_id(), "Description should match");

    }
}