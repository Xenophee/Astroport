package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.astroport.constants.Discount.MINIMUM_VISITS_FOR_DISCOUNT;
import static org.astroport.util.ConsoleColorsUtil.notificationMessage;
import static org.astroport.util.ConsoleColorsUtil.valueMessage;
import static org.astroport.util.DatesUtil.formatFullDate;
import static org.astroport.util.NumbersUtil.doubleToStringWithZero;


/**
 * This class represents a service for managing tickets.
 * It provides methods for creating, saving, updating tickets and calculating fares.
 */
public class TicketService {

    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    private final TicketDAO ticketDAO;
    private final DockSpotDAO dockSpotDAO;
    private final FareCalculatorService fareCalculatorService;

    /**
     * Constructor for the TicketService class.
     * @param ticketDAO the DAO for managing tickets
     * @param dockSpotDAO the DAO for managing dock spots
     * @param fareCalculatorService the service for calculating fares
     */
    public TicketService(TicketDAO ticketDAO, DockSpotDAO dockSpotDAO ,FareCalculatorService fareCalculatorService) {
        this.ticketDAO = ticketDAO;
        this.dockSpotDAO = dockSpotDAO;
        this.fareCalculatorService = fareCalculatorService;
    }


    /**
     * Creates and saves a ticket for a given dock spot and ship name.
     * @param dockSpot the dock spot for the ticket
     * @param shipName the name of the ship for the ticket
     */
    public void createAndSaveTicket(DockSpot dockSpot, String shipName) {
        Ticket ticket = new Ticket();
        ticket.setDockSpot(dockSpot);
        ticket.setShipName(shipName);
        ticket.setPrice(0);
        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(null);

        if (ticketDAO.saveTicket(ticket)) {
            if (ticketDAO.getNbTicket(shipName) > MINIMUM_VISITS_FOR_DISCOUNT) System.out.println("\n" + notificationMessage(messages.getString("advertDiscount")));
            printIncomingTicketDetails(dockSpot, shipName, ticket.getInTime());
        }
    }


    /**
     * Calculates and sets the fare for a given ship name and ticket.
     * @param shipName the name of the ship
     * @param ticket the ticket for which to calculate the fare
     */
    public void calculateAndSetFare(String shipName, Ticket ticket) {
        LocalDateTime outTime = LocalDateTime.now();
        ticket.setOutTime(outTime);

        boolean discount = ticketDAO.getNbTicket(shipName) > MINIMUM_VISITS_FOR_DISCOUNT;
        fareCalculatorService.calculateFare(ticket, discount);
    }


    /**
     * Updates a given ticket and its dock spot.
     * @param ticket the ticket to update
     */
    public void updateTicketAndDockSpot(Ticket ticket) {
        DockSpot dockSpot = ticket.getDockSpot();
        dockSpot.setAvailable(true);
        if (ticketDAO.updateTicket(ticket) && dockSpotDAO.updateDock(dockSpot)) {
            printExitingTicketDetails(ticket);
        }
    }


    /**
     * Prints the details of an incoming ticket.
     * @param dockSpot the dock spot of the ticket
     * @param shipName the name of the ship for the ticket
     * @param inTime the time the ship arrived
     */
    public void printIncomingTicketDetails(DockSpot dockSpot, String shipName, LocalDateTime inTime) {
        System.out.println(messages.getString("ticketSaved"));
        System.out.println(messages.getString("spotAllocated") + valueMessage(String.valueOf(dockSpot.getId())));
        System.out.println(messages.getString("recordedInTime") + valueMessage(shipName) + messages.getString("is") + valueMessage(formatFullDate(inTime, languageInterface)));
    }


    /**
     * Prints the details of an exiting ticket.
     * @param ticket the ticket to print details for
     */
    public void printExitingTicketDetails(Ticket ticket) {

        if (ticket.getPrice() == 0) {
            System.out.println("\n" + notificationMessage(messages.getString("freeDocking")));
        } else {
            System.out.println(messages.getString("payTheDock") + valueMessage(doubleToStringWithZero(ticket.getPrice(), languageInterface)));
        }

        System.out.println(messages.getString("recordedOutTime") + valueMessage(ticket.getShipName()) + messages.getString("is") + valueMessage(formatFullDate(ticket.getOutTime(), languageInterface)));
    }
}
