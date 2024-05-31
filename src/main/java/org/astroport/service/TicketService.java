package org.astroport.service;

import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.astroport.constants.Discount.MINIMUM_VISITS_FOR_DISCOUNT;
import static org.astroport.util.ConsoleColorsUtil.notificationMessage;
import static org.astroport.util.ConsoleColorsUtil.valueMessage;

public class TicketService {
    private static final LanguageUtil languageUtil = LanguageUtil.getInstance();
    private final ResourceBundle messages = languageUtil.getMessages();
    private final ResourceBundle errors = languageUtil.getErrors();

    private final TicketDAO ticketDAO;
    private final FareCalculatorService fareCalculatorService;

    public TicketService(TicketDAO ticketDAO, FareCalculatorService fareCalculatorService) {
        this.ticketDAO = ticketDAO;
        this.fareCalculatorService = fareCalculatorService;
    }

    private Ticket createAndSaveTicket(DockSpot dockSpot, String shipName) {
        Ticket ticket = new Ticket();
        ticket.setDockSpot(dockSpot);
        ticket.setShipName(shipName);
        ticket.setPrice(0);
        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(null);
        ticketDAO.saveTicket(ticket);
        return ticket;
    }

    private void updateTicketAndDockSpot(Ticket ticket) {
        if (ticketDAO.updateTicket(ticket)) {
            DockSpot dockSpot = ticket.getDockSpot();
            dockSpot.setAvailable(true);
            dockSpotDAO.updateDock(dockSpot);

            if ((ticket.getPrice() == 0)) {
                System.out.println("\n" + notificationMessage(" Dock time less than 30 minutes is free "));
            } else {
                System.out.println("\nPlease pay the Dock fare : " + valueMessage(String.valueOf(ticket.getPrice())));
            }

            System.out.println("Recorded out-time for ship name : " + valueMessage(ticket.getShipName()) + " is : " + valueMessage(String.valueOf(ticket.getOutTime())));
        } else {
            System.err.println("Unable to update ticket information. Error occurred");
        }
    }

    private void printTicketDetails(DockSpot dockSpot, String shipName, LocalDateTime inTime) {
        System.out.println("\nGenerated Ticket and saved in DB");
        System.out.println("Please park your ship in spot number : " + valueMessage(String.valueOf(dockSpot.getId())));
        System.out.println("Recorded in-time for ship name : " + valueMessage(shipName) + " is : " + valueMessage(String.valueOf(inTime)));
    }
}
