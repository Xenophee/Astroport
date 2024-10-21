package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.constants.DockType;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.optionMessage;


/**
 * This class represents a service for managing ships.
 * It provides methods for getting the ship type and name.
 */
public class ShipService {
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    public ShipService() {}


    /**
     * Gets the type of the ship.
     * Prints the options for ship types and continuously reads user input until a valid option is selected.
     * @return the selected DockType
     */
    public DockType getShipType() {
        System.out.println(messages.getString("getShipType"));
        String[] options = messages.getString("shipTypeOptions").split(", ");
        Arrays.stream(options).forEach(option -> System.out.println(optionMessage(option)));

        while (true) {
            Optional<Integer> input = InputReaderUtil.readAnInteger(languageInterface);
            if (input.isPresent() && input.get() > 0 && input.get() <= DockType.values().length) {
                return DockType.values()[input.get() - 1];
            } else {
                System.err.println(errors.getString("noExistingOption"));
            }
        }
    }


    /**
     * Gets the name of the ship.
     * Continuously reads user input until a non-empty string is entered.
     * @return the entered ship name
     */
    public String getShipName() {
        Optional<String> shipName;
        System.out.println(messages.getString("getShipName"));
        do {
            shipName = InputReaderUtil.readAString(languageInterface);
        } while (shipName.isEmpty());
        return shipName.get();
    }
}
