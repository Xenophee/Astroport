package org.astroport.service;

import org.astroport.util.InputReaderUtil;
import org.astroport.util.LanguageUtil;

import java.util.Optional;
import java.util.ResourceBundle;

import static org.astroport.util.ConsoleColorsUtil.*;

public class InteractiveShell {

    private static final InputReaderUtil inputReaderUtil = new InputReaderUtil();
    private static final LanguageUtil languageUtil = LanguageUtil.getInstance();
    private static ResourceBundle messages;


    public static void loadInterface() {

        chooseLanguage();
        messages = languageUtil.getMessagesBundle();

        System.out.println(System.lineSeparator());
        System.out.println(welcomeMessage(messages.getString("welcome")));
        System.out.println(specialMessage(italicMessage(messages.getString("slogan"))));

        loadMenu();
    }

    private static void loadMenu(){
        System.out.println("\n--------------------------------------------------");
        System.out.println(messages.getString("select"));
        System.out.println(optionMessage(messages.getString("mainOption1")));
        System.out.println(optionMessage(messages.getString("mainOption2")));
        System.out.println(optionMessage(messages.getString("mainOption3")));
        System.out.println("--------------------------------------------------");

        boolean runningApp = true;

        while (runningApp) {
            Optional<Integer> option = inputReaderUtil.readSelection();

            if (option.isPresent()) {
                switch(option.get()){
                    case 1: {
//                        dockService.processIncomingShip();
                        break;
                    }
                    case 2: {
//                        dockService.processExitingShip();
                        break;
                    }
                    case 3: {
                        System.out.println(messages.getString("exit"));
                        runningApp = false;
                        break;
                    }
                    default: System.err.println("Unsupported option. Please enter a number corresponding to the provided menu.");
                }
            }


        }
    }


    private static void chooseLanguage() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Please select a language by entering the right number : ");
        System.out.println(optionMessage("1. English"));
        System.out.println(optionMessage("2. French"));
        System.out.println("--------------------------------------------------");

        String language = null;

        while (language == null) {
            Optional<Integer> option = inputReaderUtil.readSelection();
            language = option.map(integer -> switch (integer) {
                case 1 -> "en";
                case 2 -> "fr";
                default -> null;
            }).orElse(null);

            if (language == null) {
                System.out.println("Invalid selection. Please enter 1 for English or 2 for French.");
            }
        }
        languageUtil.setLanguage(language);
    }

}
