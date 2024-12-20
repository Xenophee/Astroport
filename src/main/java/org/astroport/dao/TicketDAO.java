package org.astroport.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.AppConfig;
import org.astroport.config.DatabaseConfig;
import org.astroport.constants.DatabaseQueries;
import org.astroport.constants.DockType;
import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class represents the Data Access Object (DAO) for managing tickets.
 * It provides methods for saving, updating, and retrieving tickets.
 */
public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    public final DatabaseConfig databaseConfig;

    /**
     * Constructor for the TicketDAO class.
     * @param databaseConfig the configuration for the database
     */
    public TicketDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }


    /**
     * Saves a given ticket to the database.
     * @param ticket the ticket to save
     * @return a boolean indicating whether the save was successful
     */
    public boolean saveTicket(Ticket ticket) {
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_INSERT_NEW_TICKET)
        ) {
            ps.setInt(1, ticket.getDockSpot().getId());
            ps.setString(2, ticket.getShipName());
            ps.setDouble(3, ticket.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(ticket.getInTime()));
            ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : Timestamp.valueOf(ticket.getOutTime()));
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            logger.error("Error saving ticket info", ex);
            System.err.println(errors.getString("unableToSaveTicket"));
            return false;
        }
    }


    /**
     * Gets the number of tickets for a given ship name.
     * @param shipName the name of the ship
     * @return the number of tickets
     */
    public int getNbTicket(String shipName) {
        int nbTickets = 0;
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_GET_TICKET_COUNT_BY_SHIP_NAME);
        ) {
            ps.setString(1, shipName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) nbTickets = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error fetching ticket's number", ex);
        }
        return nbTickets;
    }


    /**
     * Gets a ticket for a given ship name.
     * @param shipName the name of the ship
     * @return an Optional containing the ticket, or an empty Optional if no ticket is found
     */
    public Optional<Ticket> getTicket(String shipName) {
        Optional<Ticket> ticket = Optional.empty();
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_GET_TICKET_BY_SHIP_NAME)
        ) {
            ps.setString(1, shipName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ticket ticketData = new Ticket();
                    DockSpot dockSpot = new DockSpot(rs.getInt(1), DockType.valueOf(rs.getString(6)), false);
                    ticketData.setDockSpot(dockSpot);
                    ticketData.setId(rs.getInt(2));
                    ticketData.setShipName(shipName);
                    ticketData.setPrice(rs.getDouble(3));

                    Timestamp inTime = rs.getTimestamp(4);
                    ticketData.setInTime((inTime != null) ? inTime.toLocalDateTime() : null);
                    Timestamp outTime = rs.getTimestamp(5);
                    ticketData.setOutTime((outTime != null) ? outTime.toLocalDateTime() : null);

                    ticket = Optional.of(ticketData);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error when trying to get the ticket", ex);
            System.err.println(errors.getString("noTicketForShip") + shipName);
        }
        return ticket;
    }


    /**
     * Updates a given ticket in the database.
     * @param ticket the ticket to update
     * @return a boolean indicating whether the update was successful
     */
    public boolean updateTicket(Ticket ticket) {
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_UPDATE_TICKET_PRICE_AND_OUT_TIME)
        ) {

            ps.setDouble(1, ticket.getPrice());
            ps.setTimestamp(2, Timestamp.valueOf(ticket.getOutTime()));
            ps.setInt(3, ticket.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            logger.error("Error saving ticket info", ex);
            System.err.println(errors.getString("unableToUpdateTicket"));
            return false;
        }
    }
}
