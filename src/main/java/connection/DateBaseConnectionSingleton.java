package connection;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DateBaseConnectionSingleton {
    private final Connection connection;
    private static DateBaseConnectionSingleton instance;

    private DateBaseConnectionSingleton() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(DateBaseConnectionSingleton.class.getClassLoader().getResourceAsStream("database.properties"));

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
    }

    public Connection openConnection() {
        return connection;
    }

    public static DateBaseConnectionSingleton getInstance() throws SQLException {
        if (instance == null || instance.openConnection().isClosed()) {
            try {
                instance = new DateBaseConnectionSingleton();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
