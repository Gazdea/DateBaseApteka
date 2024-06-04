package servlet;
import dao.MedicationDAO;
import dto.MedicationDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MedicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/medications/*")
public class MedicationServlet extends HttpServlet {
    private MedicationService medicationService;

    @Override
    public void init() {
        try {
            MedicationDAO medicationDAO = new MedicationDAO();
            medicationService = new MedicationService(medicationDAO);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MedicationDTO> medications = null;
        try {
            medications = medicationService.getAllMedicationAsDTO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("medications", medications);
        request.getRequestDispatcher("/medications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            MedicationDTO medicationDTO = new MedicationDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    medicationDTO.setName(request.getParameter("name"));
                    medicationDTO.setDescription(request.getParameter("description"));
                    try {
                        medicationService.addMedication(medicationDTO);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    response.sendRedirect("/medications");
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
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setMedication_id(Integer.parseInt(req.getParameter("updatemedicationId")));
        medicationDTO.setName(req.getParameter("updatename"));
        medicationDTO.setDescription(req.getParameter("updatedescription"));
        medicationService.updateMedication(medicationDTO);
        resp.sendRedirect("/medications");
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int medicationId = Integer.parseInt(req.getParameter("medicationId"));
        medicationService.deleteMedication(medicationId);
        resp.sendRedirect("/medications");
    }
}
