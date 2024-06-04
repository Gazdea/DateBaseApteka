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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PrescriptionDTO> prescriptions = null;
        try {
            prescriptions = prescriptionService.getAllPrescriptionsAsDTO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("prescriptions", prescriptions);
            request.getRequestDispatcher("/prescriptions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    prescriptionDTO.setPatientID(Integer.parseInt(request.getParameter("addpatientID")));
                    prescriptionDTO.setMedicationID(Integer.parseInt(request.getParameter("addmedicationID")));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        prescriptionDTO.setDate_of_prescribed(sdf.parse(request.getParameter("adddate_of_prescribed")));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    prescriptionDTO.setDosage(request.getParameter("adddosage"));
                    try {
                        prescriptionService.addPrescription(prescriptionDTO);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    response.sendRedirect("/prescriptions");
                    break;
                case "update":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
            }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionID(Integer.parseInt(req.getParameter("updateprescriptionID")));
        prescriptionDTO.setPatientID(Integer.parseInt(req.getParameter("updatepatientID")));
        prescriptionDTO.setMedicationID(Integer.parseInt(req.getParameter("updatemedicationID")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            prescriptionDTO.setDate_of_prescribed(sdf.parse(req.getParameter("updatedate_of_prescribed")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        prescriptionDTO.setDosage(req.getParameter("updatedosage"));
        try {
            prescriptionService.updatePrescription(prescriptionDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/prescriptions");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int prescriptionId = Integer.parseInt(req.getParameter("prescription_id"));
        try {
            prescriptionService.deletePrescription(prescriptionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/prescriptions");
    }
}

