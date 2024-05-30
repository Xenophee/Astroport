package org.astroport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;

public class InputReaderUtil {

    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    private static final Scanner scan = new Scanner(System.in);

    public Optional<Integer> readSelection() {
        Optional<Integer> result = Optional.empty();
        try {
            String line = scan.nextLine();
            if (line.matches("\\d+")) {
                result =  Optional.of(Integer.parseInt(line));
            } else {
                logger.error("Invalid input: not a number");
                System.err.println("Invalid input. Please enter a valid number.");
            }

        } catch (Exception e) {
            logger.error("Error while reading user input from Shell", e);
            System.err.println("Error reading input. Please try again.");
        }
        return result;
    }
}
