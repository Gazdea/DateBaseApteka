package servlet;

import dto.ComponentDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import service.ComponentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComponentServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ComponentService componentService;
    @InjectMocks
    private ComponentServlet componentServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoGet() throws IOException, SQLException, ServletException {
        List<ComponentDTO> componentDTOList = new ArrayList<>();
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponent_id(1);
        componentDTO.setName("name");
        componentDTO.setDescription("description");
        componentDTOList.add(componentDTO);

        when(componentService.getAllComponentsAsDTO()).thenReturn(componentDTOList);
        when(request.getRequestDispatcher("/components.jsp")).thenReturn(dispatcher);

        componentServlet.doGet(request,response);
        verify(request).setAttribute("components", componentDTOList);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost() throws IOException, SQLException {
        when(request.getParameter("addname")).thenReturn("name");
        when(request.getParameter("adddescription")).thenReturn("description");
        when(request.getParameter("action")).thenReturn("add");

        componentServlet.doPost(request,response);

        verify(componentService).addComponent(any(ComponentDTO.class));
        verify(response).sendRedirect("/components");
    }

    @Test
    void testDoPut() throws IOException, SQLException {
        when(request.getParameter("updateid")).thenReturn("0");
        when(request.getParameter("updatename")).thenReturn("name");
        when(request.getParameter("updatedescription")).thenReturn("description");

        componentServlet.doPut(request,response);

        verify(componentService).updateComponent(any(ComponentDTO.class));
    }

    @Test
    void testDoDelete() throws IOException, SQLException {
        when(request.getParameter("componentId")).thenReturn("0");
        componentServlet.doDelete(request,response);
        verify(componentService).deleteComponent(0);
    }
}