package org.astroport.service;

import org.astroport.constants.DockType;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.optionMessage;

public class ShipService {
    private final InputReaderUtil inputReaderUtil;
    private static final LanguageUtil languageUtil = LanguageUtil.getInstance();
    private final ResourceBundle messages = languageUtil.getMessages();
    private final ResourceBundle errors = languageUtil.getErrors();

    public ShipService(InputReaderUtil inputReaderUtil) {
        this.inputReaderUtil = inputReaderUtil;
    }

    private DockType getShipType() {
        while (true) {
            System.out.println(messages.getString("getShipType"));
            String[] options = messages.getString("shipTypeOptions").split(", ");
            Arrays.stream(options).forEach(option -> System.out.println(optionMessage(option)));
            Optional<Integer> input = inputReaderUtil.readSelection();

            if (input.isPresent() && input.get() > 0 && input.get() <= DockType.values().length) {
                return DockType.values()[input.get() - 1];
            } else {
                System.err.println(errors.getString("noExistingOption"));
            }
        }
    }

    private String getShipName() {
        Optional<String> shipName = Optional.empty();
        while (shipName.isEmpty()) {
            System.out.println(messages.getString("getShipName"));
            shipName = inputReaderUtil.readAString();
        }
        return shipName.get();
    }
}
