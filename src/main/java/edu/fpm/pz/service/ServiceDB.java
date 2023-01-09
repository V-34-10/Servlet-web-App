package edu.fpm.pz.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ServiceDB {

    private static final String DB_PATH = "db/db.properties";
    /**
     * Gets a connection from the properties specified in the file
     * db.properties @return the database connection
     */
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in =
                     ServiceDB.class.getClassLoader().getResourceAsStream(DB_PATH)) {
            props.load(in);
        }
        String drivers = props.getProperty("driver");
        if (drivers != null) {
            System.setProperty("driver", drivers);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
