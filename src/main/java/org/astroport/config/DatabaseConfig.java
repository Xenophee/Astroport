package org.astroport.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    private final Properties properties;

    public DatabaseConfig() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {

            if (input == null) {
                logger.error("Unable to find database.properties");
                System.err.println("Unable to connect to database. Please contact an administrator of Astroport.");
                return;
            }

            properties.load(input);

        } catch (IOException ex) {
            logger.error("Error while reading database.properties file", ex);
        }
    }

    public Connection getConnection() throws SQLException {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        String driver = properties.getProperty("database.driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            logger.error("Error while connecting to database", ex);
        }

        return DriverManager.getConnection(url, user, password);
    }
}