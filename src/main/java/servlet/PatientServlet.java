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
        catch ( SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PatientDTO> patients = null;
        try {
            patients = patientService.getAllPatientsAsDTO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("patients", patients);
            request.getRequestDispatcher("/patients.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PatientDTO patientDTO = new PatientDTO();
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                patientDTO.setName(request.getParameter("addname"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    patientDTO.setBirth_date(sdf.parse(request.getParameter("addbirth_date")));
                    patientService.addPatient(patientDTO);
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("/patients");
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
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatient_id(Integer.parseInt(req.getParameter("updatepatientId")));
        patientDTO.setName(req.getParameter("updatename"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            patientDTO.setBirth_date(sdf.parse(req.getParameter("updatebirth_date")));
            patientService.updatePatient(patientDTO);
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/patients");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            int patientId = Integer.parseInt(req.getParameter("patientId"));
        try {
            patientService.deletePatient(patientId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/patients");
    }
}
