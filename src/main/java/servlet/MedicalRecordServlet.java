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
        try {

            MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

            medicalRecordService = new MedicalRecordService(medicalRecordDAO);
        } catch (SQLException |IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MedicalRecordDTO> medicalRecords = medicalRecordService.getAllMedicalRecordAsDTO();
            request.setAttribute("medicalrecords", medicalRecords);
            request.getRequestDispatcher("/medicalrecords.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    medicalRecordDTO.setPatient_id(Integer.parseInt(request.getParameter("patient_id")));
                    medicalRecordDTO.setRecord_details(request.getParameter("record_details"));
                    medicalRecordService.addMedicalRecord(medicalRecordDTO);
                    response.sendRedirect("/medicalrecords");
                    break;
                case "update":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
            }
        }catch (IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
                medicalRecordDTO.setRecord_id(Integer.parseInt(req.getParameter("updaterecord_id")));
                medicalRecordDTO.setPatient_id(Integer.parseInt(req.getParameter("updatepatient_id")));
                medicalRecordDTO.setRecord_details(req.getParameter("updaterecord_details"));
                medicalRecordService.updateMedicalRecord(medicalRecordDTO);
                resp.sendRedirect("/medicalrecords");
        } catch (IOException | SQLException e){
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
                int recordId = Integer.parseInt(req.getParameter("recordId"));
                medicalRecordService.deleteMedicalRecord(recordId);
                resp.sendRedirect("/medicalrecords");
        } catch (IOException | SQLException e){
            e.fillInStackTrace();
        }
    }
}
