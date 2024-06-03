package servlet;
import dao.PatientDAO;
import dto.PatientDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PatientService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    private PatientService patientService;

    @Override
    public void init() {
        try {
            PatientDAO patientDAO = new PatientDAO();
            patientService = new PatientService(patientDAO);
        }
        catch ( SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PatientDTO> patients = patientService.getAllPatientsAsDTO();
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/patients.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PatientDTO patientDTO = new PatientDTO();
            String action = request.getParameter("action");
            if (action.equals("add")) {
                patientDTO.setName(request.getParameter("addname"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                patientDTO.setBirth_date(sdf.parse(request.getParameter("addbirth_date")));
                patientService.addPatient(patientDTO);
                response.sendRedirect("/patients");

            } else if (action.equals("update")) {
                patientDTO.setPatient_id(Integer.parseInt(request.getParameter("updatepatientId")));
                patientDTO.setName(request.getParameter("updatename"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                patientDTO.setBirth_date(sdf.parse(request.getParameter("updatebirth_date")));
                patientService.updatePatient(patientDTO);
                response.sendRedirect("/patients");

            } else if (action.equals("delete")) {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                patientService.deletePatient(patientId);
                response.sendRedirect("/patients");
            }
        } catch (IOException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

}
