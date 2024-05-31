package org.astroport.util;



public class ConsoleColorsUtil {
    public static final String RESET = "\033[0m";  // Text Reset

    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String WHITE_BACKGROUND = "\033[47m";


    public static String welcomeMessage(String message) {
        return BLACK_BOLD + WHITE_BACKGROUND + message +  RESET;
    }

    public static String notificationMessage(String message) {
        return BLACK_BOLD + BLUE_BACKGROUND + message +  RESET;
    }

    public static String valueMessage(String message) {
        return CYAN + message +  RESET;
    }

    public static String optionMessage(String message) {
        return BLUE + message +  RESET;
    }

    public static String specialMessage(String message) {
        return BLUE_BOLD_BRIGHT + message +  RESET;
    }

    public static String italicMessage(String message) {
        return "\033[3m" + message + "\033[0m";
    }

}
