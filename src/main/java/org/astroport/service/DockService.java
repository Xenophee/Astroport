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

import static org.astroport.constants.Discount.MINIMUM_VISITS_FOR_DISCOUNT;
import static org.astroport.util.ConsoleColorsUtil.*;

public class DockService {

    private static final Logger logger = LogManager.getLogger("DockService");
    private final LanguageUtil languageInterface;
    private final ResourceBundle messages;
    private final ResourceBundle errors;

    private final DockSpotDAO dockSpotDAO;
    private final TicketDAO ticketDAO;
    private final ShipService shipService;
    private final TicketService ticketService;

    public DockService(DockSpotDAO dockSpotDAO, TicketDAO ticketDAO, ShipService shipService, TicketService ticketService) {
        this.languageInterface = AppConfig.getLanguageInterface();
        this.messages = languageInterface.getMessages();
        this.errors = languageInterface.getErrors();

        this.dockSpotDAO = dockSpotDAO;
        this.ticketDAO = ticketDAO;
        this.shipService = shipService;
        this.ticketService = ticketService;
    }

    public void processIncomingShip() {
        try {
            Optional<DockSpot> dockSpot = getNextDockNumberIfAvailable();
            if (dockSpot.isPresent()) {
                String shipName = shipService.getShipName();
                dockSpot.get().setAvailable(false);
                dockSpotDAO.updateDock(dockSpot.get());

                Ticket ticket = ticketService.createAndSaveTicket(dockSpot.get(), shipName);

                if (ticketDAO.getNbTicket(shipName) > MINIMUM_VISITS_FOR_DISCOUNT) {
                    System.out.println("\n" + notificationMessage(" Welcome back ! As a recurring Astroport user, you'll benefit from a 5% discount. "));
                }

                ticketService.printTicketDetails(dockSpot.get(), shipName, ticket.getInTime());

                Main.resetApp();
            }
        } catch (Exception e) {
            logger.error("Unable to process incoming ship", e);
        }
    }


    public Optional<DockSpot> getNextDockNumberIfAvailable() {
        DockType dockType = shipService.getShipType();
        Optional<Integer> dockNumber = dockSpotDAO.getNextAvailableSlot(dockType);

        if (dockNumber.isPresent() && dockNumber.get() > 0) {
            return Optional.of(new DockSpot(dockNumber.get(), dockType, true));
        } else {
            System.err.println("Dock slots are full. Please try again later.");
            return Optional.empty();
        }
    }


    public void processExitingShip() {
        try {
            String shipName = shipService.getShipName();
            Ticket ticket = ticketDAO.getTicket(shipName);

            if (ticket == null) {
                System.err.println("No ticket found for the ship name : " + shipName);
                return;
            }

            ticketService.calculateAndSetFare(shipName, ticket);
            ticketService.updateTicketAndDockSpot(ticket);

        } catch (Exception e) {
            logger.error("Unable to process exiting ship", e);
        }

        Main.resetApp();
    }

}
