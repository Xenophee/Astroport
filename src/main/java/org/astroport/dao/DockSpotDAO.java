package org.astroport.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.AppConfig;
import org.astroport.config.DatabaseConfig;
import org.astroport.constants.DatabaseQueries;
import org.astroport.constants.DockType;
import org.astroport.model.DockSpot;
import org.astroport.util.LanguageUtil;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class represents the Data Access Object (DAO) for managing dock spots.
 * It provides methods for getting the next available slot and updating a dock spot.
 */
public class DockSpotDAO {

    private static final Logger logger = LogManager.getLogger("DockSpotDAO");
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    public final DatabaseConfig databaseConfig;

    /**
     * Constructor for the DockSpotDAO class.
     * @param databaseConfig the configuration for the database
     */
    public DockSpotDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }


    /**
     * Gets the next available slot for a given dock type.
     * @param dockType the type of the dock
     * @return an Optional containing the next available slot number, or an empty Optional if no slot is available
     */
    public Optional<Integer> getNextAvailableSlot(DockType dockType) {
        Optional<Integer> result = Optional.empty();
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_GET_NEXT_AVAILABLE_DOCK)
        ) {
            ps.setString(1, dockType.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = Optional.of(rs.getInt(1)).filter(dockNumber -> dockNumber > 0);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching dock number from DB", ex);
        }
        return result;
    }


    /**
     * Updates a given dock spot.
     * @param dockSpot the dock spot to update
     * @return a boolean indicating whether the update was successful
     */
    public boolean updateDock(DockSpot dockSpot) {
        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(DatabaseQueries.QUERY_UPDATE_DOCK_AVAILABILITY)
        ) {

            ps.setBoolean(1, dockSpot.isAvailable());
            ps.setInt(2, dockSpot.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            logger.error("Error updating dock info", ex);
            System.err.println(errors.getString("unableToUpdateDock"));
            return false;
        }
    }
}

