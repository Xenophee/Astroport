package org.astroport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;


/**
 * This class provides utility methods for reading user input.
 * It includes methods for reading an integer and a string from the console.
 */
public class InputReaderUtil {

    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    private static final Scanner scan = new Scanner(System.in);


    /**
     * Reads an integer from the console.
     * If the input is not a valid integer, it logs an error and returns an empty Optional.
     * @param languageInterface the language interface for localization
     * @return an Optional containing the integer read from the console, or an empty Optional if the input is not a valid integer
     */
    public static Optional<Integer> readAnInteger(LanguageUtil languageInterface) {
        try {
            return Optional.of(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            logger.error("Invalid input : not a number", e);
            System.err.println(languageInterface.getErrors().getString("invalidNumberInput"));
            return Optional.empty();
        }
    }


    /**
     * Reads a string from the console.
     * If the input is an empty string, it returns an empty Optional.
     * @param languageInterface the language interface for localization
     * @return an Optional containing the string read from the console, or an empty Optional if the input is an empty string
     */
    public static Optional<String> readAString(LanguageUtil languageInterface) {
        String stringScan = scan.nextLine().trim();
        if (stringScan.isEmpty()) {
            System.err.println(languageInterface.getErrors().getString("invalidStringInput"));
            return Optional.empty();
        }
        return Optional.of(stringScan);
    }
}
