package org.astroport.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.config.DatabaseConfig;
import org.astroport.constants.DatabaseQueries;
import org.astroport.constants.DockType;
import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;

import java.sql.*;

public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");

    public DatabaseConfig databaseConfig = new DatabaseConfig();


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
            return ps.execute();
        } catch (SQLException ex) {
            logger.error("Error saving ticket info", ex);
            return false;
        }
    }


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


    public Ticket getTicket(String shipName) {
        Ticket ticket = null;
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_GET_TICKET_BY_SHIP_NAME)
        ) {
            ps.setString(1, shipName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    DockSpot dockSpot = new DockSpot(rs.getInt(1), DockType.valueOf(rs.getString(6)), false);
                    ticket.setDockSpot(dockSpot);
                    ticket.setId(rs.getInt(2));
                    ticket.setShipName(shipName);
                    ticket.setPrice(rs.getDouble(3));

                    Timestamp inTime = rs.getTimestamp(4);
                    ticket.setInTime((inTime != null) ? inTime.toLocalDateTime() : null);
                    Timestamp outTime = rs.getTimestamp(5);
                    ticket.setOutTime((outTime != null) ? outTime.toLocalDateTime() : null);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching next available slot", ex);
        }
        return ticket;
    }


    public boolean updateTicket(Ticket ticket) {
        try (
                Connection con = databaseConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(DatabaseQueries.QUERY_UPDATE_TICKET_PRICE_AND_OUT_TIME)
        ) {

            ps.setDouble(1, ticket.getPrice());
            ps.setTimestamp(2, Timestamp.valueOf(ticket.getOutTime()));
            ps.setInt(3, ticket.getId());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            logger.error("Error saving ticket info", ex);
        }
        return false;
    }
}
