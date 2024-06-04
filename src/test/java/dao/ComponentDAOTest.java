package dao;

import model.ComponentBuilder;
import model.MedicationBuilder;
import model.MedicationComponentBuilder;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComponentDAOTest {
    @Container
    private  static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    private ComponentDAO componentDAO;
    private MedicationDAO medicationDAO;
    private MedicationComponentDAO medicationComponentDAO;

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
        componentDAO = new ComponentDAO(connection);
        medicationDAO = new MedicationDAO(connection);
        medicationComponentDAO = new MedicationComponentDAO(connection);
    }

    @AfterAll
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testAddComponentAndGetById() throws SQLException {
        ComponentBuilder componentBuilder = new ComponentBuilder.Builder()
                .setName("test1")
                .setDescription("test1")
                .build();
        int id = componentDAO.addComponent(componentBuilder);
        ComponentBuilder componentFromBD = componentDAO.getComponentById(id);

        assertNotNull(componentFromBD,"Should not be null");
        assertEquals(componentBuilder.getName(), componentFromBD.getName(),"Component should match");
        assertEquals(componentBuilder.getDescription(), componentFromBD.getDescription(), "Description should match");
    }

    @Test
    void testDeleteCar() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setComponent_id(1)
                .setDescription("test1")
                .setName("test1")
                .build();

        int id = componentDAO.addComponent(component);
        componentDAO.deleteComponent(id);

        ComponentBuilder deletecomponentFromDb = componentDAO.getComponentById(id);
        assertNull(deletecomponentFromDb,"Should be null");
    }

    @Test
    void testUpdateComponent() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setDescription("test1")
                .setName("test1")
                .build();

        int id = componentDAO.addComponent(component);

        ComponentBuilder upadtecomponent = new ComponentBuilder.Builder()
                .setComponent_id(id)
                .setDescription("test2")
                .setName("test2")
                .build();

        componentDAO.updateComponent(upadtecomponent);
        ComponentBuilder componentFromBD = componentDAO.getComponentById(id);

        assertEquals(upadtecomponent.getName(), componentFromBD.getName(),"Component should match");
        assertEquals(upadtecomponent.getDescription(), componentFromBD.getDescription(), "Description should match");
    }

    @Test
    void testgetComponentByMedicamentId() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();
        List<ComponentBuilder> componets  = new ArrayList<>();
        componets.add(component);

        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setDescription("test")
                .setName("test")
                .build();

        int idcomponent = componentDAO.addComponent(component);
        int idmedical = medicationDAO.addMedicationBuilder(medicationBuilder);

        MedicationComponentBuilder medicationComponentBuilder = new MedicationComponentBuilder.Builder()
                .setComponentId(idcomponent)
                .setMedicationId(idmedical)
                .build();

        medicationComponentDAO.addMedicationComponent(medicationComponentBuilder);

        List<ComponentBuilder> componentFromBD = componentDAO.getComponentByMedicamentId(idmedical);

        assertEquals(component.getName(), componentFromBD.get(0).getName(),"Component should match");
        assertEquals(component.getDescription(), componentFromBD.get(0).getDescription(), "Description should match");
    }
}