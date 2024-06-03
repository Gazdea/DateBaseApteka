package servlet;
import dao.PrescriptionDAO;
import dto.PrescriptionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PrescriptionService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/prescriptions")
public class PrescriptionServlet extends HttpServlet {
    private PrescriptionService prescriptionService;

    @Override
    public void init() {
        try {
            PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
            prescriptionService = new PrescriptionService(prescriptionDAO);
        }
        catch ( SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PrescriptionDTO> prescriptions = prescriptionService.getAllPrescriptionsAsDTO();
            request.setAttribute("prescriptions", prescriptions);
            request.getRequestDispatcher("/prescriptions.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
            String action = request.getParameter("action");
            if (action.equals("add")) {
                prescriptionDTO.setPatientID(Integer.parseInt(request.getParameter("addpatientID")));
                prescriptionDTO.setMedicationID(Integer.parseInt(request.getParameter("addmedicationID")));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                prescriptionDTO.setDate_of_prescribed(sdf.parse(request.getParameter("adddate_of_prescribed")));
                prescriptionDTO.setDosage(request.getParameter("adddosage"));
                prescriptionService.addPrescription(prescriptionDTO);
                response.sendRedirect("/prescriptions");
            } else if (action.equals("update")) {
                prescriptionDTO.setPrescriptionID(Integer.parseInt(request.getParameter("updateprescriptionID")));
                prescriptionDTO.setPatientID(Integer.parseInt(request.getParameter("updatepatientID")));
                prescriptionDTO.setMedicationID(Integer.parseInt(request.getParameter("updatemedicationID")));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                prescriptionDTO.setDate_of_prescribed(sdf.parse(request.getParameter("updatedate_of_prescribed")));
                prescriptionDTO.setDosage(request.getParameter("updatedosage"));
                prescriptionService.updatePrescription(prescriptionDTO);
                response.sendRedirect("/prescriptions");
            } else if (action.equals("delete")) {
                int prescriptionId = Integer.parseInt(request.getParameter("prescription_id"));
                prescriptionService.deletePrescription(prescriptionId);
                response.sendRedirect("/prescriptions");
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
