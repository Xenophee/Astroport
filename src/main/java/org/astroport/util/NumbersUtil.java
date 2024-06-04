package org.astroport.util;

import org.astroport.AppConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumbersUtil {

    public static double roundDecimals(double number, int scale) {
        return BigDecimal.valueOf(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static String doubleToStringWithZero(double number, LanguageUtil languageInterface) {
        Locale currentLocale = Locale.forLanguageTag(languageInterface.getLanguage());
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(currentLocale);
        DecimalFormat format = new DecimalFormat("#.00", symbols);
        return format.format(number);
    }
}
