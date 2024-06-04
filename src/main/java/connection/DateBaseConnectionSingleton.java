package connection;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DateBaseConnectionSingleton {
    private static Connection connection = null;
    private static DateBaseConnectionSingleton instance;

    private DateBaseConnectionSingleton() throws ClassNotFoundException, SQLException, IOException {
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

    public static DateBaseConnectionSingleton getInstance() throws SQLException, IOException, ClassNotFoundException {
        if (instance == null || instance.openConnection().isClosed()) {
            instance = new DateBaseConnectionSingleton();
        }
        return instance;
    }
}
