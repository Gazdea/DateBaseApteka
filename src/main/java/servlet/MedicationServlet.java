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
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MedicationDTO> medications = medicationService.getAllMedicationAsDTO();
            request.setAttribute("medications", medications);
            request.getRequestDispatcher("/medications.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            MedicationDTO medicationDTO = new MedicationDTO();
            MedicationComponentDTO medComponentDTO = new MedicationComponentDTO();
            String action = request.getParameter("action");
            if (action.equals("add")) {
                medicationDTO.setName(request.getParameter("name"));
                medicationDTO.setDescription(request.getParameter("description"));
                medicationService.addMedication(medicationDTO);
                response.sendRedirect("/medications");

            } else if (action.equals("update")) {
                medicationDTO.setMedication_id(Integer.parseInt(request.getParameter("updatemedicationId")));
                medicationDTO.setName(request.getParameter("updatename"));
                medicationDTO.setDescription(request.getParameter("updatedescription"));
                medicationService.updateMedication(medicationDTO);
                response.sendRedirect("/medications");

            } else if (action.equals("delete")) {
                int medicationId = Integer.parseInt(request.getParameter("medicationId"));
                medicationService.deleteMedication(medicationId);
                response.sendRedirect("/medications");

            } else if (action.equals("addcomponent")) {
                medComponentDTO.setMedicationId(Integer.parseInt(request.getParameter("addmedicamentid")));
                medComponentDTO.setComponentId(Integer.parseInt(request.getParameter("addcomponentid")));
                System.out.println(medComponentDTO.getMedicationId());
                System.out.println(medComponentDTO.getComponentId());
                medComponentService.addMedicationComponent(medComponentDTO);
                response.sendRedirect("/medications");

            } else if(action.equals("deletecomponent")) {
                int medicationId = Integer.parseInt(request.getParameter("deletemedication_id"));
                int componentId = Integer.parseInt(request.getParameter("deletecomponent_id"));
                medComponentService.deleteMedicationComponent(medicationId, componentId);
                response.sendRedirect("/medications");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MedicationDTO medicationDTO = new MedicationDTO();
            MedicationComponentDTO medComponentDTO = new MedicationComponentDTO();
            String action = req.getParameter("action");
        }
        catch (Exception e) {}
    }
}
