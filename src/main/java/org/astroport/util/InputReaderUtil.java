package org.astroport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;

public class InputReaderUtil {

    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    private static final Scanner scan = new Scanner(System.in);


    public static Optional<Integer> readAnInteger() {
        try {
            return Optional.of(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            logger.error("Invalid input : not a number", e);
            System.err.println("Invalid input. Please enter a valid number.");
            return Optional.empty();
        }
    }

    public static Optional<String> readAString() {
        String stringScan = scan.nextLine().trim();
        if (stringScan.isEmpty()) {
            logger.error("Invalid input : empty string");
            System.err.println("Invalid input. Please enter a valid string.");
            return Optional.empty();
        }
        return Optional.of(stringScan);
    }
}
