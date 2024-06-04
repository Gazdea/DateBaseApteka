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
import java.util.List;

@WebServlet("/prescriptions")
public class PrescriptionServlet extends HttpServlet {
    private PrescriptionService prescriptionService;

    @Override
    public void init() {
        try {
            PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
            prescriptionService = new PrescriptionService(prescriptionDAO);
        } catch (SQLException | IOException e) {
            e.fillInStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PrescriptionDTO> prescriptions = prescriptionService.getAllPrescriptionsAsDTO();
            request.setAttribute("prescriptions", prescriptions);
            request.getRequestDispatcher("/prescriptions.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    prescriptionDTO.setPatientID(Integer.parseInt(request.getParameter("addpatientID")));
                    prescriptionDTO.setMedicationID(Integer.parseInt(request.getParameter("addmedicationID")));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    prescriptionDTO.setDate_of_prescribed(sdf.parse(request.getParameter("adddate_of_prescribed")));
                    prescriptionDTO.setDosage(request.getParameter("adddosage"));
                    prescriptionService.addPrescription(prescriptionDTO);
                    response.sendRedirect("/prescriptions");
                    break;
                case "update":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
                prescriptionDTO.setPrescriptionID(Integer.parseInt(req.getParameter("updateprescriptionID")));
                prescriptionDTO.setPatientID(Integer.parseInt(req.getParameter("updatepatientID")));
                prescriptionDTO.setMedicationID(Integer.parseInt(req.getParameter("updatemedicationID")));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                prescriptionDTO.setDate_of_prescribed(sdf.parse(req.getParameter("updatedate_of_prescribed")));
                prescriptionDTO.setDosage(req.getParameter("updatedosage"));
                prescriptionService.updatePrescription(prescriptionDTO);
                resp.sendRedirect("/prescriptions");
        } catch ( SQLException | ParseException | IOException e){
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
                int prescriptionId = Integer.parseInt(req.getParameter("prescription_id"));
                prescriptionService.deletePrescription(prescriptionId);
                resp.sendRedirect("/prescriptions");
        } catch (SQLException | IOException e){
            e.fillInStackTrace();
        }
    }
}

