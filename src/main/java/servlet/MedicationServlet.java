package servlet;
import dao.MedicationComponentDAO;
import dao.MedicationDAO;
import dto.MedicationComponentDTO;
import dto.MedicationDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MedicationComponentService;
import service.MedicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/medications/*")
public class MedicationServlet extends HttpServlet {
    private MedicationService medicationService;
    private MedicationComponentService medComponentService;

    @Override
    public void init() {
        try {
            MedicationDAO medicationDAO = new MedicationDAO();
            medicationService = new MedicationService(medicationDAO);
            MedicationComponentDAO medComponentDAO = new MedicationComponentDAO();
            medComponentService = new MedicationComponentService(medComponentDAO);
        } catch (SQLException | IOException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MedicationDTO> medications = medicationService.getAllMedicationAsDTO();
            request.setAttribute("medications", medications);
            request.getRequestDispatcher("/medications.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            MedicationDTO medicationDTO = new MedicationDTO();
            MedicationComponentDTO medComponentDTO = new MedicationComponentDTO();
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    medicationDTO.setName(request.getParameter("name"));
                    medicationDTO.setDescription(request.getParameter("description"));
                    medicationService.addMedication(medicationDTO);
                    response.sendRedirect("/medications");
                    break;

                case "addcomponent":
                    medComponentDTO.setMedicationId(Integer.parseInt(request.getParameter("addmedicamentid")));
                    medComponentDTO.setComponentId(Integer.parseInt(request.getParameter("addcomponentid")));
                    System.out.println(medComponentDTO.getMedicationId());
                    System.out.println(medComponentDTO.getComponentId());
                    medComponentService.addMedicationComponent(medComponentDTO);
                    response.sendRedirect("/medications");
                    break;

                case "deletecomponent": {
                    int medicationId = Integer.parseInt(request.getParameter("deletemedication_id"));
                    int componentId = Integer.parseInt(request.getParameter("deletecomponent_id"));
                    medComponentService.deleteMedicationComponent(medicationId, componentId);
                    response.sendRedirect("/medications");
                    break;
                }
                case "update":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
            }
        } catch (IOException | SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MedicationDTO medicationDTO = new MedicationDTO();
                medicationDTO.setMedication_id(Integer.parseInt(req.getParameter("updatemedicationId")));
                medicationDTO.setName(req.getParameter("updatename"));
                medicationDTO.setDescription(req.getParameter("updatedescription"));
                medicationService.updateMedication(medicationDTO);
                resp.sendRedirect("/medications");
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
                int medicationId = Integer.parseInt(req.getParameter("medicationId"));
                medicationService.deleteMedication(medicationId);
                resp.sendRedirect("/medications");
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
