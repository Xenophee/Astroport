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

public class TicketService {
    private final LanguageUtil languageInterface;
    private final ResourceBundle messages;
    private final ResourceBundle errors;

    private final TicketDAO ticketDAO;
    private final DockSpotDAO dockSpotDAO;
    private final FareCalculatorService fareCalculatorService;

    public TicketService(TicketDAO ticketDAO, DockSpotDAO dockSpotDAO ,FareCalculatorService fareCalculatorService) {
        this.languageInterface = AppConfig.getLanguageInterface();
        this.messages = languageInterface.getMessages();
        this.errors = languageInterface.getErrors();

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

    public void printTicketDetails(DockSpot dockSpot, String shipName, LocalDateTime inTime) {
        System.out.println("\nGenerated Ticket and saved in DB");
        System.out.println("Please park your ship in spot number : " + valueMessage(String.valueOf(dockSpot.getId())));
        System.out.println("Recorded in-time for ship name : " + valueMessage(shipName) + " is : " + valueMessage(String.valueOf(inTime)));
    }
}
