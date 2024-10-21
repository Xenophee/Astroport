package org.astroport.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * This class provides utility methods for handling numbers.
 * It includes methods for rounding decimals and converting doubles to strings with zero decimal places.
 */
public class NumbersUtil {


    /**
     * Rounds a given double value to a specified number of decimal places.
     * @param number the double value to round
     * @param scale the number of decimal places to round to
     * @return the rounded double value
     */
    public static double roundDecimals(double number, int scale) {
        return BigDecimal.valueOf(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }


    /**
     * Converts a given double value to a string with zero decimal places.
     * The conversion is done according to the current language interface.
     * @param number the double value to convert
     * @param languageInterface the language interface for localization
     * @return the converted string
     */
    public static String doubleToStringWithZero(double number, LanguageUtil languageInterface) {
        Locale currentLocale = Locale.forLanguageTag(languageInterface.getLanguage());
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(currentLocale);
        DecimalFormat format = new DecimalFormat("#.00", symbols);
        return format.format(number);
    }
}
