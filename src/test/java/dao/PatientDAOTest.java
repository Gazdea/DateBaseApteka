package dao;

import model.ComponentBuilder;
import model.PatientBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatientDAOTest {
    @Container
    private  static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

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
        patientDAO = new PatientDAO(connection);
    }

    @AfterAll
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void addPatientAndGetPatientId() {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        PatientBuilder patientFromBD = patientDAO.getPatientById(idpatient);
        assertNotNull(patientFromBD,"Should not be null");
        assertEquals(patientFromBD.getName(), patientBuilder.getName(),"Patient should match");
    }

    @Test
    void updatePatient() {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        PatientBuilder patientBuilder2 = new PatientBuilder.Builder()
                .setPatientID(idpatient)
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        patientDAO.updatePatient(patientBuilder2);

        PatientBuilder patientFromBD = patientDAO.getPatientById(idpatient);

        assertEquals(patientFromBD.getName(), patientBuilder.getName(),"Patient should match");
    }

    @Test
    void deletePatient() {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        patientDAO.deletePatient(idpatient);
        PatientBuilder patientFromBD = patientDAO.getPatientById(idpatient);
        assertNull(patientFromBD,"Should not be null");
    }

    @Test
    void getPatientAll() {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        PatientBuilder patientBuilder2 = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient2 = patientDAO.addPatient(patientBuilder2);

        List<PatientBuilder> patientBuilderList = patientDAO.getAllPatients();

        assertTrue(patientBuilderList.stream().anyMatch(p-> p.getName().equals(patientBuilder.getName())), "Medical Record should match");
        assertTrue(patientBuilderList.stream().anyMatch(p-> p.getName().equals(patientBuilder2.getName())), "Medical Record should match");
    }
}