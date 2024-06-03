package servlet;

import dao.MedicalRecordDAO;
import dao.PatientDAO;
import dto.MedicalRecordDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MedicalRecordService;
import service.PatientService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/medicalrecords")
public class MedicalRecordServlet extends HttpServlet {
    private  PatientService patientService;
    private  MedicalRecordService medicalRecordService;

    @Override
    public void init() {
        try {
            PatientDAO patientDAO = new PatientDAO();
            MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
            patientService = new PatientService(patientDAO);
            medicalRecordService = new MedicalRecordService(medicalRecordDAO);
        } catch (SQLException |IOException e) {
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
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
            String action = request.getParameter("action");
            if (action.equals("add")) {
                medicalRecordDTO.setPatient_id(Integer.parseInt(request.getParameter("patient_id")));
                medicalRecordDTO.setRecord_details(request.getParameter("record_details"));
                medicalRecordService.addMedicalRecord(medicalRecordDTO);
                response.sendRedirect("/medicalrecords");

            } else if (action.equals("update")) {
                medicalRecordDTO.setRecord_id(Integer.parseInt(request.getParameter("updaterecord_id")));
                medicalRecordDTO.setPatient_id(Integer.parseInt(request.getParameter("updatepatient_id")));
                medicalRecordDTO.setRecord_details(request.getParameter("updaterecord_details"));
                medicalRecordService.updateMedicalRecord(medicalRecordDTO);
                response.sendRedirect("/medicalrecords");

            } else if (action.equals("delete")) {
                int recordId = Integer.parseInt(request.getParameter("recordId"));
                medicalRecordService.deleteMedicalRecord(recordId);
                response.sendRedirect("/medicalrecords");
            }
//            } else if (action.equals("select")) {
//                int recordId = Integer.parseInt(request.getParameter("recordId"));
//                MedicalRecordDTO medicalRecord = medicalRecordService.getMedicalRecordById(recordId);
//                PatientDTO patients = patientService.getPatientById(medicalRecord.getPatient_id());
//                request.setAttribute("patients", patients);
//                request.getRequestDispatcher("/medicalrecords.jsp").forward(request, response);
//            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
