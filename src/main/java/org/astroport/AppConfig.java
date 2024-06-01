package org.astroport;

import org.astroport.config.DatabaseConfig;
import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.service.*;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Optional;

import static org.astroport.util.ConsoleColorsUtil.optionMessage;

public class AppConfig {

    private static LanguageUtil languageInterface;

    public static LanguageUtil getLanguageInterface() {
        return languageInterface;
    }

    public static InteractiveShell initializeConfig() {

        languageInterface = new LanguageUtil();
        chooseLanguage();

        DatabaseConfig databaseConfig = new DatabaseConfig();
        DockSpotDAO dockSpotDAO = new DockSpotDAO(databaseConfig);
        TicketDAO ticketDAO = new TicketDAO(databaseConfig);


        FareCalculatorService fareCalculatorService = new FareCalculatorService();
        ShipService shipService = new ShipService();
        TicketService ticketService = new TicketService(ticketDAO, dockSpotDAO, fareCalculatorService);
        DockService dockService = new DockService(dockSpotDAO, ticketDAO, shipService, ticketService);

        return new InteractiveShell(dockService);
    }

    private static void chooseLanguage() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Please select a language by entering the right number : ");
        System.out.println(optionMessage("1. English"));
        System.out.println(optionMessage("2. French"));
        System.out.println("--------------------------------------------------");

        String language = null;

        while (language == null) {
            Optional<Integer> option = InputReaderUtil.readAnInteger();
            language = option.map(integer -> switch (integer) {
                case 1 -> "en";
                case 2 -> "fr";
                default -> null;
            }).orElse(null);

            if (language == null) {
                System.out.println("Invalid selection. Please enter 1 for English or 2 for French.");
            }
        }
        languageInterface.setLanguage(language);
    }
}
