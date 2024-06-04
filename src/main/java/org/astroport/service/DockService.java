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
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    private final DockSpotDAO dockSpotDAO;
    private final TicketDAO ticketDAO;
    private final ShipService shipService;
    private final TicketService ticketService;

    public DockService(DockSpotDAO dockSpotDAO, TicketDAO ticketDAO, ShipService shipService, TicketService ticketService) {
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
                if (ticketDAO.getNbTicket(shipName) > MINIMUM_VISITS_FOR_DISCOUNT) System.out.println("\n" + notificationMessage(messages.getString("advertDiscount")));
                ticketService.printIncomingTicketDetails(dockSpot.get(), shipName, ticket.getInTime());
            }
        } catch (Exception e) {
            logger.error("Unable to process incoming ship", e);
            System.err.println(errors.getString("unableToProcessIncomingShip"));
        } finally {
            Main.resetApp();
        }
    }


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
