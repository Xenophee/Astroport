package org.astroport.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.config.DatabaseConfig;
import org.astroport.constants.DatabaseQueries;
import org.astroport.constants.DockType;
import org.astroport.model.DockSpot;

import java.sql.*;
import java.util.Optional;


public class DockSpotDAO {
    private static final Logger logger = LogManager.getLogger("DockSpotDAO");

    public DatabaseConfig databaseConfig;

    public DockSpotDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }


    public Optional<Integer> getNextAvailableSlot(DockType dockType) {
        Optional<Integer> result = Optional.empty();
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_GET_NEXT_AVAILABLE_DOCK)
        ) {
            ps.setString(1, dockType.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = Optional.of(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching dock number from DB. Parking slots might be full", ex);
        }
        return result;
    }


    public boolean updateDock(DockSpot dockSpot) {
        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(DatabaseQueries.QUERY_UPDATE_DOCK_AVAILABILITY)
        ) {

            statement.setBoolean(1, dockSpot.isAvailable());
            statement.setInt(2, dockSpot.getId());

            int updatedRows = statement.executeUpdate();
            return updatedRows == 1;

        } catch (SQLException ex) {
            logger.error("Error updating dock info", ex);
            return false;
        }
    }
}

