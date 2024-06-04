package dao;

import connection.DateBaseConnectionSingleton;
import model.ComponentBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ComponentDAOTest {

    @Mock
    private DateBaseConnectionSingleton ConnectionSingleton;

    @Mock
    private java.sql.Connection mockConnection;

    @Mock
    private java.sql.PreparedStatement mockPreparedStatement;

    @Mock
    private java.sql.ResultSet mockResultSet;

    @InjectMocks
    private ComponentDAO componentDAO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(ConnectionSingleton.openConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testGetAllComponents() throws SQLException, IOException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("component_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Component 1");
        when(mockResultSet.getString("description")).thenReturn("Description 1");

        List<ComponentBuilder> components = componentDAO.getAllComponents();

        assertEquals(1, components.size());
        assertEquals(1, components.get(0).getComponent_id());
        assertEquals("Component 1", components.get(0).getName());
        assertEquals("Description 1", components.get(0).getDescription());
    }

    @Test
    public void testAddComponent() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setName("Test Component")
                .setDescription("Test Description")
                .build();

        componentDAO.addComponent(component);

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateComponent() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setName("Test Component")
                .setDescription("Test Description")
                .build();
        componentDAO.addComponent(component);
        ComponentBuilder updatedComponent = new ComponentBuilder.Builder()
                .setComponent_id(1)
                .setName("Updated Test Component")
                .setDescription("Updated Test Description")
                .build();
        componentDAO.updateComponent(updatedComponent);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    @Test
    public void testDeleteComponent() throws SQLException {
        ComponentBuilder component = new ComponentBuilder.Builder()
                .setName("Test Component")
                .setDescription("Test Description")
                .build();
        componentDAO.addComponent(component);
        componentDAO.deleteComponent(1);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    @Test
    public void testGetComponentById() throws SQLException {
        // Prepare mock result set
        when(mockResultSet.next()).thenReturn(true); // Mocking that result set has at least one row
        when(mockResultSet.getInt("component_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Test Component");
        when(mockResultSet.getString("description")).thenReturn("Test Description");

        // Call the method under test
        ComponentBuilder component = componentDAO.getComponentById(1);

        // Verify interactions
        verify(mockPreparedStatement, times(1)).setInt(1, 1); // Verify preparedStatement.setInt(1, id) is called
        verify(mockPreparedStatement, times(1)).executeQuery(); // Verify executeQuery() is called
        verify(mockResultSet, times(1)).next(); // Verify next() is called
        verify(mockResultSet, times(1)).getInt("component_id"); // Verify getInt("component_id") is called
        verify(mockResultSet, times(1)).getString("name"); // Verify getString("name") is called
        verify(mockResultSet, times(1)).getString("description"); // Verify getString("description") is called

        // Verify the returned component
        assertNotNull(component);
        assertEquals(1, component.getComponent_id());
        assertEquals("Test Component", component.getName());
        assertEquals("Test Description", component.getDescription());
    }
}