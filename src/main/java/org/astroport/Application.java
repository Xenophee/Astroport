package org.astroport;

import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.service.DockService;
import org.astroport.service.FareCalculatorService;
import org.astroport.service.InteractiveShell;
import org.astroport.service.ShipService;
import org.astroport.util.InputReaderUtil;

public class Application {

    public InteractiveShell createInteractiveShell() {
        InputReaderUtil inputReaderUtil = createInputReaderUtil();
        return new InteractiveShell(inputReaderUtil);
    }

    public DockService createDockService() {
        InputReaderUtil inputReaderUtil = createInputReaderUtil();
        DockSpotDAO dockSpotDAO = createDockSpotDAO();
        TicketDAO ticketDAO = createTicketDAO();
        ShipService shipService = createShipService();
        return new DockService(inputReaderUtil, dockSpotDAO, ticketDAO, shipService);
    }

    public InputReaderUtil createInputReaderUtil() {
        return new InputReaderUtil();
    }

    public FareCalculatorService createFareCalculatorService() {
        return new FareCalculatorService();
    }

    public ShipService createShipService() {
        InputReaderUtil inputReaderUtil = createInputReaderUtil();
        return new ShipService(inputReaderUtil);
    }

    public DockSpotDAO createDockSpotDAO() {
        return new DockSpotDAO();
    }

    public TicketDAO createTicketDAO() {
        return new TicketDAO();
    }
}
