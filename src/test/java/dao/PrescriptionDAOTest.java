package dao;

import model.MedicationBuilder;
import model.PatientBuilder;
import model.PrescriptionBuilder;
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
class PrescriptionDAOTest {
    @Container
    private  static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    private PrescriptionDAO prescriptionDAO;
    private PatientDAO patientDAO;
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
        patientDAO = new PatientDAO(connection);
        prescriptionDAO = new PrescriptionDAO(connection);
    }

    @AfterAll
    public void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void getAllPrescriptions() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setName("test")
                .setDescription("test")
                .build();

        int idmedicament = medicationDAO.addMedicationBuilder(medicationBuilder);

        PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient)
                .setMedicationID(idmedicament)
                .setDosage("test")
                .setDate_of_prescribed(new Date(123))
                .build();

        prescriptionDAO.addPrescription(prescriptionBuilder);

        PatientBuilder patientBuilder2 = new PatientBuilder.Builder()
                .setName("test2")
                .setBirth_date(new Date(123))
                .build();

        int idpatient2 = patientDAO.addPatient(patientBuilder2);

        MedicationBuilder medicationBuilder2 = new MedicationBuilder.Builder()
                .setName("test2")
                .setDescription("test2")
                .build();

        int idmedicament2 = medicationDAO.addMedicationBuilder(medicationBuilder2);

        PrescriptionBuilder prescriptionBuilder2 = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient2)
                .setMedicationID(idmedicament2)
                .setDosage("test2")
                .setDate_of_prescribed(new Date(123))
                .build();

        prescriptionDAO.addPrescription(prescriptionBuilder2);

        List<PrescriptionBuilder> prescriptions = prescriptionDAO.getAllPrescriptions();

        assertNotNull(prescriptions);
        assertTrue(prescriptions.stream().anyMatch(p -> p.getDosage().equals(prescriptionBuilder.getDosage())));
        assertTrue(prescriptions.stream().anyMatch(p -> p.getDosage().equals(prescriptionBuilder2.getDosage())));
    }

    @Test
    void addPrescriptionAndGetById() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setName("test")
                .setDescription("test")
                .build();

        int idmedicament = medicationDAO.addMedicationBuilder(medicationBuilder);

        PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient)
                .setMedicationID(idmedicament)
                .setDosage("test")
                .setDate_of_prescribed(new Date(123))
                .build();

        int idprescription = prescriptionDAO.addPrescription(prescriptionBuilder);

        PrescriptionBuilder prescriptionFromBd  = prescriptionDAO.getPrescriptionByID(idprescription);

        assertNotNull(prescriptionFromBd);
        assertEquals(prescriptionFromBd.getMedicationID(), idmedicament);
        assertEquals(prescriptionFromBd.getDosage(), prescriptionBuilder.getDosage());
    }

    @Test
    void updatePrescription() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setName("test")
                .setDescription("test")
                .build();

        int idmedicament = medicationDAO.addMedicationBuilder(medicationBuilder);

        PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient)
                .setMedicationID(idmedicament)
                .setDosage("test")
                .setDate_of_prescribed(new Date(123))
                .build();

        int idprescription = prescriptionDAO.addPrescription(prescriptionBuilder);

        PrescriptionBuilder updatePrescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient)
                .setMedicationID(idmedicament)
                .setPrescriptionID(idprescription)
                .setDosage("test2")
                .setDate_of_prescribed(new Date(123))
                .build();

        prescriptionDAO.updatePrescription(updatePrescriptionBuilder);

        PrescriptionBuilder prescriptionFromBd  = prescriptionDAO.getPrescriptionByID(idprescription);

        assertEquals(prescriptionFromBd.getDosage(), updatePrescriptionBuilder.getDosage());
    }

    @Test
    void deletePrescription() throws SQLException {
        PatientBuilder patientBuilder = new PatientBuilder.Builder()
                .setName("test")
                .setBirth_date(new Date(123))
                .build();

        int idpatient = patientDAO.addPatient(patientBuilder);

        MedicationBuilder medicationBuilder = new MedicationBuilder.Builder()
                .setName("test")
                .setDescription("test")
                .build();

        int idmedicament = medicationDAO.addMedicationBuilder(medicationBuilder);

        PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder.Builder()
                .setPatientID(idpatient)
                .setMedicationID(idmedicament)
                .setDosage("test")
                .setDate_of_prescribed(new Date(123))
                .build();

        int idprescription = prescriptionDAO.addPrescription(prescriptionBuilder);

        prescriptionDAO.deletePrescription(idprescription);

        PrescriptionBuilder prescriptionBuilder1 = prescriptionDAO.getPrescriptionByID(idprescription);

        assertNull(prescriptionBuilder1);
    }
}