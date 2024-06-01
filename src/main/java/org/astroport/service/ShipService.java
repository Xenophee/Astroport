package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.constants.DockType;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.optionMessage;

public class ShipService {
    private final LanguageUtil languageInterface;
    private final ResourceBundle messages;
    private final ResourceBundle errors;

    public ShipService() {
        this.languageInterface = AppConfig.getLanguageInterface();
        this.messages = languageInterface.getMessages();
        this.errors = languageInterface.getErrors();
    }


    public DockType getShipType() {
        while (true) {
            System.out.println(messages.getString("getShipType"));
            String[] options = messages.getString("shipTypeOptions").split(", ");
            Arrays.stream(options).forEach(option -> System.out.println(optionMessage(option)));
            Optional<Integer> input = InputReaderUtil.readAnInteger();

            if (input.isPresent() && input.get() > 0 && input.get() <= DockType.values().length) {
                return DockType.values()[input.get() - 1];
            } else {
                System.err.println(errors.getString("noExistingOption"));
            }
        }
    }

    public String getShipName() {
        Optional<String> shipName = Optional.empty();
        while (shipName.isEmpty()) {
            System.out.println(messages.getString("getShipName"));
            shipName = InputReaderUtil.readAString();
        }
        return shipName.get();
    }
}
