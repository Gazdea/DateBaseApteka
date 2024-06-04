import dao.MedicationDAO;
import dao.PatientDAO;
import model.MedicationBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        PatientDAO patientDAO = new PatientDAO();
        System.out.println(patientDAO.getAllPatients());
    }
}
