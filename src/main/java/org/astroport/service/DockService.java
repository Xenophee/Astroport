package org.astroport.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.astroport.AppConfig;
import org.astroport.Main;
import org.astroport.constants.DockType;
import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class represents the service for managing docks.
 * It provides methods for processing incoming and exiting ships.
 */
public class DockService {

    private static final Logger logger = LogManager.getLogger("DockService");
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    private final DockSpotDAO dockSpotDAO;
    private final TicketDAO ticketDAO;
    private final ShipService shipService;
    private final TicketService ticketService;

    /**
     * Constructor for the DockService class.
     * @param dockSpotDAO the DAO for managing dock spots
     * @param ticketDAO the DAO for managing tickets
     * @param shipService the service for managing ships
     * @param ticketService the service for managing tickets
     */
    public DockService(DockSpotDAO dockSpotDAO, TicketDAO ticketDAO, ShipService shipService, TicketService ticketService) {
        this.dockSpotDAO = dockSpotDAO;
        this.ticketDAO = ticketDAO;
        this.shipService = shipService;
        this.ticketService = ticketService;
    }


    /**
     * Processes an incoming ship.
     * Gets the next available dock number, sets it as unavailable, and creates and saves a ticket for the ship.
     */
    public void processIncomingShip() {
        try {
            Optional<DockSpot> dockSpot = getNextDockNumberIfAvailable();
            if (dockSpot.isPresent()) {
                String shipName = shipService.getShipName();
                dockSpot.get().setAvailable(false);

                if (dockSpotDAO.updateDock(dockSpot.get())) ticketService.createAndSaveTicket(dockSpot.get(), shipName);
            }
        } catch (Exception e) {
            logger.error("Unable to process incoming ship", e);
            System.err.println(errors.getString("unableToProcessIncomingShip"));
        } finally {
            Main.resetApp();
        }
    }


    /**
     * Gets the next available dock number if available.
     * @return an Optional containing the next available DockSpot, or an empty Optional if no dock is available
     */
    public Optional<DockSpot> getNextDockNumberIfAvailable() {
        DockType dockType = shipService.getShipType();
        Optional<Integer> dockNumber = dockSpotDAO.getNextAvailableSlot(dockType);

        if (dockNumber.isPresent()) {
            return Optional.of(new DockSpot(dockNumber.get(), dockType, true));
        } else {
            System.err.println(errors.getString("noAvailableDock"));
            return Optional.empty();
        }
    }


    /**
     * Processes an exiting ship.
     * Gets the ship's ticket, calculates and sets the fare, and updates the ticket and dock spot.
     */
    public void processExitingShip() {
        try {
            String shipName;
            Optional<Ticket> ticket;

            do {
                shipName = shipService.getShipName();
                ticket = ticketDAO.getTicket(shipName);
            } while (ticket.isEmpty());

            ticketService.calculateAndSetFare(shipName, ticket.get());
            ticketService.updateTicketAndDockSpot(ticket.get());

        } catch (Exception e) {
            logger.error("Unable to process exiting ship", e);
            System.err.println(errors.getString("unableToProcessExitingShip"));
        } finally {
            Main.resetApp();
        }
    }
}
