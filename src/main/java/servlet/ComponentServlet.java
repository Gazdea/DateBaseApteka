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
        ComponentDAO componentDAO = new ComponentDAO();
        componentService = new ComponentService(componentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<ComponentDTO> components = null;
        try {
            components = componentService.getAllComponentsAsDTO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("components", components);
        request.getRequestDispatcher("/components.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ComponentDTO componentDTO = new ComponentDTO();
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                componentDTO.setName(request.getParameter("addname"));
                componentDTO.setDescription(request.getParameter("adddescription"));
                try {
                    componentService.addComponent(componentDTO);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("/components");
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
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponent_id(Integer.parseInt(req.getParameter("updateid")));
        componentDTO.setName(req.getParameter("updatename"));
        componentDTO.setDescription(req.getParameter("updatedescription"));
        try {
            componentService.updateComponent(componentDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/components");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int componentId = Integer.parseInt(req.getParameter("componentId"));
        try {
            componentService.deleteComponent(componentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/components");
    }
}
