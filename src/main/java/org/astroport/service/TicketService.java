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

public class TicketService {
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    private final TicketDAO ticketDAO;
    private final DockSpotDAO dockSpotDAO;
    private final FareCalculatorService fareCalculatorService;

    public TicketService(TicketDAO ticketDAO, DockSpotDAO dockSpotDAO ,FareCalculatorService fareCalculatorService) {
        this.ticketDAO = ticketDAO;
        this.dockSpotDAO = dockSpotDAO;
        this.fareCalculatorService = fareCalculatorService;
    }

    public Ticket createAndSaveTicket(DockSpot dockSpot, String shipName) {
        Ticket ticket = new Ticket();
        ticket.setDockSpot(dockSpot);
        ticket.setShipName(shipName);
        ticket.setPrice(0);
        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(null);
        ticketDAO.saveTicket(ticket);
        return ticket;
    }

    public void calculateAndSetFare(String shipName, Ticket ticket) {
        LocalDateTime outTime = LocalDateTime.now();
        ticket.setOutTime(outTime);

        boolean discount = ticketDAO.getNbTicket(shipName) > MINIMUM_VISITS_FOR_DISCOUNT;
        fareCalculatorService.calculateFare(ticket, discount);
    }

    public void updateTicketAndDockSpot(Ticket ticket) {
        DockSpot dockSpot = ticket.getDockSpot();
        dockSpot.setAvailable(true);
        if (ticketDAO.updateTicket(ticket) && dockSpotDAO.updateDock(dockSpot)) {
            printExitingTicketDetails(ticket);
        }
    }


    public void printIncomingTicketDetails(DockSpot dockSpot, String shipName, LocalDateTime inTime) {
        System.out.println(messages.getString("ticketSaved"));
        System.out.println(messages.getString("spotAllocated") + valueMessage(String.valueOf(dockSpot.getId())));
        System.out.println(messages.getString("recordedInTime") + valueMessage(shipName) + messages.getString("is") + valueMessage(formatFullDate(inTime, languageInterface)));
    }


    public void printExitingTicketDetails(Ticket ticket) {

        if (ticket.getPrice() == 0) {
            System.out.println("\n" + notificationMessage(messages.getString("freeDocking")));
        } else {
            System.out.println(messages.getString("payTheDock") + valueMessage(doubleToStringWithZero(ticket.getPrice(), languageInterface)));
        }

        System.out.println(messages.getString("recordedOutTime") + valueMessage(ticket.getShipName()) + messages.getString("is") + valueMessage(formatFullDate(ticket.getOutTime(), languageInterface)));
    }
}
