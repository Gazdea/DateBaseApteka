package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ComponentDAO;
import dto.ComponentDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ComponentService;

@WebServlet("/components")
public class ComponentServlet extends HttpServlet {
    private ComponentService componentService;

    @Override
    public void init() {
        try {
            ComponentDAO componentDAO = new ComponentDAO();
            componentService = new ComponentService(componentDAO);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<ComponentDTO> components = componentService.getAllComponentsAsDTO();
            request.setAttribute("components", components);
            request.getRequestDispatcher("/components.jsp").forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            ComponentDTO componentDTO = new ComponentDTO();
            String action = request.getParameter("action");
            if (action.equals("add")) {
                componentDTO.setName(request.getParameter("addname"));
                componentDTO.setDescription(request.getParameter("adddescription"));
                componentService.addComponent(componentDTO);
                response.sendRedirect("/components");

            } else if (action.equals("update")) {
                componentDTO.setComponent_id(Integer.parseInt(request.getParameter("updateid")));
                componentDTO.setName(request.getParameter("updatename"));
                componentDTO.setDescription(request.getParameter("updatedescription"));
                componentService.updateComponent(componentDTO);
                response.sendRedirect("/components");

            } else if (action.equals("delete")) {
                int componentId = Integer.parseInt(request.getParameter("componentId"));
                componentService.deleteComponent(componentId);
                response.sendRedirect("/components");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
