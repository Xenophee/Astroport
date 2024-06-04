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
    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();

    public ShipService() {
    }


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

    public String getShipName() {
        Optional<String> shipName;
        System.out.println(messages.getString("getShipName"));
        do {
            shipName = InputReaderUtil.readAString(languageInterface);
        } while (shipName.isEmpty());
        return shipName.get();
    }
}
