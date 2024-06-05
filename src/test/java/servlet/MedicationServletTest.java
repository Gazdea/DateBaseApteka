package servlet;


import dto.MedicationDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.MedicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private MedicationService medicationService;
    @InjectMocks
    private MedicationServlet medicationServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoGet() throws IOException, ServletException, SQLException {
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setMedication_id(1);
        medicationDTO.setName("name");
        medicationDTO.setDescription("description");
        medicationDTOList.add(medicationDTO);

        when(medicationService.getAllMedicationAsDTO()).thenReturn(medicationDTOList);
        when(request.getRequestDispatcher("/medications.jsp")).thenReturn(dispatcher);

        medicationServlet.doGet(request, response);

        verify(request).setAttribute("medications", medicationDTOList);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost() throws IOException, SQLException {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("description")).thenReturn("description");

        medicationServlet.doPost(request, response);

        verify(medicationService).addMedication(any(MedicationDTO.class));
        verify(response).sendRedirect("/medications");
    }

    @Test
    void testDoPut() throws IOException, SQLException {
        when(request.getParameter("updatemedicationId")).thenReturn("1");
        when(request.getParameter("updatename")).thenReturn("updatedName");
        when(request.getParameter("updatedescription")).thenReturn("updatedDescription");

        medicationServlet.doPut(request, response);

        verify(medicationService).updateMedication(any(MedicationDTO.class));
        verify(response).sendRedirect("/medications");
    }

    @Test
    void testDoDelete() throws IOException {
        when(request.getParameter("medicationId")).thenReturn("1");

        medicationServlet.doDelete(request, response);

        verify(medicationService).deleteMedication(1);
        verify(response).sendRedirect("/medications");
    }
}