package connection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateBaseConnectionSingletonTest {

    @Test
    void testConnection() throws SQLException, IOException, ClassNotFoundException {
        DateBaseConnectionSingleton dateBaseConnectionSingleton = DateBaseConnectionSingleton.getInstance();
        Connection connection = dateBaseConnectionSingleton.openConnection();
        assertNotNull(connection, "Connection should not be null");
        assertFalse(connection.isClosed(),"Connection should be open");
    }

    @Test
    void testSingleton() throws SQLException, IOException, ClassNotFoundException {
        DateBaseConnectionSingleton dateBaseConnectionSingleton = DateBaseConnectionSingleton.getInstance();
        DateBaseConnectionSingleton dateBaseConnectionSingleton1 = DateBaseConnectionSingleton.getInstance();

        assertEquals(dateBaseConnectionSingleton,dateBaseConnectionSingleton1,"Must be the same");
    }

    @Test
    void testReconnectingAfterClose() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DateBaseConnectionSingleton.getInstance().openConnection();
        connection.close();

        assertTrue(connection.isClosed(),"Connection should be closed");

        connection = DateBaseConnectionSingleton.getInstance().openConnection();
        assertFalse(connection.isClosed(), "Connection should be open");
    }
}