package servlet;

import dto.MedicationComponentDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.MedicationComponentService;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationComponentServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private MedicationComponentService medicationComponentService;
    @InjectMocks
    private MedicationComponentServlet medicationComponentServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost() throws IOException, SQLException {
        when(request.getParameter("action")).thenReturn("addcomponent");
        when(request.getParameter("addmedicamentid")).thenReturn("1");
        when(request.getParameter("addcomponentid")).thenReturn("2");

        medicationComponentServlet.doPost(request, response);

        verify(medicationComponentService).addMedicationComponent(any(MedicationComponentDTO.class));
        verify(response).sendRedirect("/medications");
    }

    @Test
    void testDoDelete() throws IOException, SQLException {
        when(request.getParameter("deletemedication_id")).thenReturn("1");
        when(request.getParameter("deletecomponent_id")).thenReturn("2");

        medicationComponentServlet.doDelete(request, response);

        verify(medicationComponentService).deleteMedicationComponent(1, 2);
        verify(response).sendRedirect("/medications");
    }
}