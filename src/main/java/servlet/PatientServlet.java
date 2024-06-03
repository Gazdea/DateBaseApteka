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
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PatientDTO> patients = patientService.getAllPatientsAsDTO();
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/patients.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PatientDTO patientDTO = new PatientDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    patientDTO.setName(request.getParameter("addname"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    patientDTO.setBirth_date(sdf.parse(request.getParameter("addbirth_date")));
                    patientService.addPatient(patientDTO);
                    response.sendRedirect("/patients");
                    break;
                case "update":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
            }
        } catch (IOException | SQLException | ParseException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PatientDTO patientDTO = new PatientDTO();
                patientDTO.setPatient_id(Integer.parseInt(req.getParameter("updatepatientId")));
                patientDTO.setName(req.getParameter("updatename"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                patientDTO.setBirth_date(sdf.parse(req.getParameter("updatebirth_date")));
                patientService.updatePatient(patientDTO);
                resp.sendRedirect("/patients");
        } catch (IOException | SQLException | ParseException e)
        {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
                int patientId = Integer.parseInt(req.getParameter("patientId"));
                patientService.deletePatient(patientId);
                resp.sendRedirect("/patients");
        } catch (IOException | SQLException  e)
        {
            e.fillInStackTrace();
        }
    }
}
