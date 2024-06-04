package org.astroport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;

public class InputReaderUtil {

    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    private static final Scanner scan = new Scanner(System.in);


    public static Optional<Integer> readAnInteger(LanguageUtil languageInterface) {
        try {
            return Optional.of(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            logger.error("Invalid input : not a number", e);
            System.err.println(languageInterface.getErrors().getString("invalidNumberInput"));
            return Optional.empty();
        }
    }

    public static Optional<String> readAString(LanguageUtil languageInterface) {
        String stringScan = scan.nextLine().trim();
        if (stringScan.isEmpty()) {
            System.err.println(languageInterface.getErrors().getString("invalidStringInput"));
            return Optional.empty();
        }
        return Optional.of(stringScan);
    }
}
