package dao;

import model.MedicationBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MedicationDAOTest {
    @Container
    private  static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    private MedicationDAO medicationDAO;

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
        medicationDAO = new MedicationDAO(connection);
    }

    @AfterAll
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testAddMedicationAndGetById() throws SQLException {
        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();

        int idmedical = medicationDAO.addMedicationBuilder(medicationBuilder);

        MedicationBuilder medicationFromBD = medicationDAO.getMedicationById(idmedical);

        assertNotNull(medicationFromBD,"Should not be null");
        assertEquals(medicationBuilder.getName(), medicationFromBD.getName(),"Component should match");
        assertEquals(medicationBuilder.getDescription(), medicationFromBD.getDescription(), "Description should match");
    }

    @Test
    void updateMedicationBuilder() throws SQLException {
        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();

        int idmedical = medicationDAO.addMedicationBuilder(medicationBuilder);

        MedicationBuilder updatemedicationBuilder = new MedicationBuilder.Builder()
                .setmMedication_id(idmedical)
                .setDescription("test2")
                .setName("test2")
                .build();

        medicationDAO.updateMedicationBuilder(updatemedicationBuilder);

        MedicationBuilder medicationFromBD = medicationDAO.getMedicationById(idmedical);

        assertEquals(updatemedicationBuilder.getName(), medicationFromBD.getName(),"Component should match");
        assertEquals(updatemedicationBuilder.getDescription(), medicationFromBD.getDescription(), "Description should match");
    }

    @Test
    void deleteMedicationBuilder() throws SQLException {
        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();

        int idmedical = medicationDAO.addMedicationBuilder(medicationBuilder);
        medicationDAO.deleteMedicationBuilder(idmedical);

        MedicationBuilder medicationFromBD = medicationDAO.getMedicationById(idmedical);

        assertNull(medicationFromBD,"Should not be null");

    }

    @Test
    void getMedicationAll() throws SQLException {
        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();

        medicationDAO.addMedicationBuilder(medicationBuilder);


        MedicationBuilder medicationBuilder2 = new MedicationBuilder.Builder()
                .setDescription("test2")
                .setName("test2")
                .build();

        medicationDAO.addMedicationBuilder(medicationBuilder2);

        List<MedicationBuilder> medicationFromBD = medicationDAO.getAllMedicationBuilders();

        assertNotNull(medicationFromBD,"Should not be null");
        assertTrue(medicationFromBD.stream().anyMatch(p-> p.getDescription().equals(medicationBuilder.getDescription()) && p.getName().equals(medicationBuilder.getName())), "Medical  should match");
        assertTrue(medicationFromBD.stream().anyMatch(p-> p.getDescription().equals(medicationBuilder2.getDescription())  && p.getName().equals(medicationBuilder2.getName())), "Medical  should match");

    }
}