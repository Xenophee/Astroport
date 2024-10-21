package org.astroport;

import org.astroport.config.DatabaseConfig;
import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.service.*;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Optional;

import static org.astroport.util.ConsoleColorsUtil.optionMessage;


/**
 * This class is responsible for the application configuration.
 * It initializes the necessary services, DAOs, and other utilities.
 */
public class AppConfig {

    private static LanguageUtil languageInterface;


    /**
     * Returns the language interface.
     * @return the language interface
     */
    public static LanguageUtil getLanguageInterface() {
        return languageInterface;
    }


    /**
     * Initializes the application configuration.
     * This includes setting up the language interface, database configuration, DAOs, and services.
     * @return an instance of InteractiveShell
     */
    public static InteractiveShell initializeConfig() {

        languageInterface = new LanguageUtil("us");
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


    /**
     * Prompts the user to choose a language.
     * The user can choose between English (US) and French.
     */
    private static void chooseLanguage() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Please select a language by entering the right number : ");
        System.out.println(optionMessage("1. English (US)"));
        System.out.println(optionMessage("2. French"));
        System.out.println("--------------------------------------------------");

        String language = null;

        while (language == null) {
            Optional<Integer> option = InputReaderUtil.readAnInteger(languageInterface);
            language = option.map(integer -> switch (integer) {
                case 1 -> "us";
                case 2 -> "fr";
                default -> null;
            }).orElse(null);

            if (language == null) {
                System.err.println("Invalid selection. Please enter 1 for English (US) or 2 for French.");
            }
        }
        languageInterface.setLanguage(language);
    }
}
