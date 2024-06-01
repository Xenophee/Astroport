package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.*;

public class InteractiveShell {
;
    private final LanguageUtil languageInterface;
    private ResourceBundle messages;
    private ResourceBundle errors;

    private final DockService dockService;

    public InteractiveShell(DockService dockService) {
        this.languageInterface = AppConfig.getLanguageInterface();
        this.messages = languageInterface.getMessages();
        this.errors = languageInterface.getErrors();

        this.dockService = dockService;
    }


    public void loadInterface() {

        System.out.println(System.lineSeparator());
        System.out.println(welcomeMessage(messages.getString("welcome")));
        System.out.println(specialMessage(italicMessage(messages.getString("slogan"))));

        loadMenu();
    }

    private void loadMenu(){
        System.out.println("\n--------------------------------------------------");
        System.out.println(messages.getString("select"));
        System.out.println(optionMessage(messages.getString("mainOption1")));
        System.out.println(optionMessage(messages.getString("mainOption2")));
        System.out.println(optionMessage(messages.getString("mainOption3")));
        System.out.println("--------------------------------------------------");

        boolean runningApp = true;

        while (runningApp) {
            Optional<Integer> option = InputReaderUtil.readAnInteger();

            if (option.isPresent()) {
                switch(option.get()){
                    case 1: {
                        dockService.processIncomingShip();
                        break;
                    }
                    case 2: {
                        dockService.processExitingShip();
                        break;
                    }
                    case 3: {
                        System.out.println(messages.getString("exit"));
                        runningApp = false;
                        break;
                    }
                    default: System.err.println(errors.getString("noExistingOption"));
                }
            }

        }
    }

}
