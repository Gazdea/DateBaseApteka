import dao.ComponentDAO;
import dao.MedicalRecordDAO;
import dao.MedicationDAO;
import dao.PatientDAO;
import model.ComponentBuilder;
import model.MedicalRecordBuilder;
import model.MedicationBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        System.out.println(new MedicalRecordDAO().getMedicalRecordById(1));
    }
}
