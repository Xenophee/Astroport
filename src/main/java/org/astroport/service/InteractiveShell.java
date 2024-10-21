package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.*;

/**
 * This class represents an interactive shell for the application.
 * It provides a user interface for interacting with the DockService.
 */
public class InteractiveShell {

    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    private final DockService dockService;

    /**
     * Constructor for the InteractiveShell class.
     * @param dockService the dock service to be used for processing ships
     */
    public InteractiveShell(DockService dockService) {
        this.dockService = dockService;
    }

    /**
     * Loads the user interface for the application.
     * Prints welcome messages and loads the main menu.
     */
    public void loadInterface() {

        System.out.println(System.lineSeparator());
        System.out.println(welcomeMessage(messages.getString("welcome")));
        System.out.println(specialMessage(italicMessage(messages.getString("slogan"))));

        loadMenu();
    }


    /**
     * Loads the main menu for the application.
     * Provides options for processing incoming and exiting ships, and for exiting the system.
     * Continuously reads user input and processes the selected option.
     */
    private void loadMenu() {
        System.out.println("\n--------------------------------------------------");
        System.out.println(messages.getString("select"));
        System.out.println(optionMessage(messages.getString("incomingShipOption")));
        System.out.println(optionMessage(messages.getString("exitingShipOption")));
        System.out.println(optionMessage(messages.getString("exitSystemOption")));
        System.out.println("--------------------------------------------------");

        while (true) {
            Optional<Integer> option = InputReaderUtil.readAnInteger(languageInterface);
            if (option.isPresent()) {
                switch (option.get()) {
                    case 1 -> dockService.processIncomingShip();
                    case 2 -> dockService.processExitingShip();
                    case 3 -> {
                        System.out.println(messages.getString("exit"));
                        System.exit(0);
                    }
                    default -> System.err.println(errors.getString("noExistingOption"));
                }
            }
        }
    }

}
