package servlet;

import dao.MedicationComponentDAO;
import dto.MedicationComponentDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MedicationComponentService;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/medicationss/*")
public class MedicationComponentServlet extends HttpServlet {
    private MedicationComponentService medComponentService;

    @Override
    public void init() {
        try {
            MedicationComponentDAO medComponentDAO = new MedicationComponentDAO();
            medComponentService = new MedicationComponentService(medComponentDAO);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MedicationComponentDTO medComponentDTO = new MedicationComponentDTO();
        String action = request.getParameter("action");
        switch (action) {
            case "addcomponent":
                medComponentDTO.setMedicationId(Integer.parseInt(request.getParameter("addmedicamentid")));
                medComponentDTO.setComponentId(Integer.parseInt(request.getParameter("addcomponentid")));
                System.out.println(medComponentDTO.getMedicationId());
                System.out.println(medComponentDTO.getComponentId());
                try {
                    medComponentService.addMedicationComponent(medComponentDTO);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("/medications");
                break;
            case "deletecomponent":
                doDelete(request, response);
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int medicationId = Integer.parseInt(req.getParameter("deletemedication_id"));
        int componentId = Integer.parseInt(req.getParameter("deletecomponent_id"));
        try {
            medComponentService.deleteMedicationComponent(medicationId, componentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/medications");
    }
}
