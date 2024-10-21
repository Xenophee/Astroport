package org.astroport.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.AppConfig;
import org.astroport.util.LanguageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This class is responsible for the configuration of the database.
 * It reads the database properties from a file and provides a method to establish a connection to the database.
 */
public class DatabaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    private final Properties properties;

    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    /**
     * Constructor for the DatabaseConfig class.
     * It reads the database properties from a file.
     */
    public DatabaseConfig() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {

            if (input == null) {
                logger.error("Unable to find database.properties");
                System.err.println(errors.getString("unableToConnectToDatabase"));
                return;
            }

            properties.load(input);

        } catch (IOException ex) {
            logger.error("Error while reading database.properties file", ex);
            System.err.println(errors.getString("unableToConnectToDatabase"));
        }
    }


    /**
     * Establishes a connection to the database using the properties read from the file.
     * @return a Connection object representing the connection to the database
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        String driver = properties.getProperty("database.driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            logger.error("Error while connecting to database", ex);
            System.err.println(errors.getString("unableToConnectToDatabase"));
        }

        return DriverManager.getConnection(url, user, password);
    }
}