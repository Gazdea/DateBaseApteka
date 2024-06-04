package servlet;

import dao.MedicalRecordDAO;
import dto.MedicalRecordDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MedicalRecordService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/medicalrecords")
public class MedicalRecordServlet extends HttpServlet {
    private  MedicalRecordService medicalRecordService;

    @Override
    public void init() {
        MedicalRecordDAO medicalRecordDAO = null;
        try {
            medicalRecordDAO = new MedicalRecordDAO();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        medicalRecordService = new MedicalRecordService(medicalRecordDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<MedicalRecordDTO> medicalRecords = null;
        try {
            medicalRecords = medicalRecordService.getAllMedicalRecordAsDTO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("medicalrecords", medicalRecords);
        request.getRequestDispatcher("/medicalrecords.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                medicalRecordDTO.setPatient_id(Integer.parseInt(request.getParameter("patient_id")));
                medicalRecordDTO.setRecord_details(request.getParameter("record_details"));
                try {
                    medicalRecordService.addMedicalRecord(medicalRecordDTO);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("/medicalrecords");
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
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setRecord_id(Integer.parseInt(req.getParameter("updaterecord_id")));
        medicalRecordDTO.setPatient_id(Integer.parseInt(req.getParameter("updatepatient_id")));
        medicalRecordDTO.setRecord_details(req.getParameter("updaterecord_details"));
        try {
            medicalRecordService.updateMedicalRecord(medicalRecordDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/medicalrecords");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int recordId = Integer.parseInt(req.getParameter("recordId"));
        try {
            medicalRecordService.deleteMedicalRecord(recordId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/medicalrecords");
    }
}
