package org.astroport.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;


/**
 * This class provides utility methods for handling dates.
 * It includes a method for formatting a LocalDateTime object into a string representation.
 */
public class DatesUtil {


    /**
     * Formats a given LocalDateTime object into a string representation.
     * The formatting is done according to the current language interface.
     * The date and time are converted to the UTC timezone.
     * @param dateTime the LocalDateTime object to format
     * @param languageInterface the language interface for localization
     * @return the string representation of the date and time
     */
    public static String formatFullDate(LocalDateTime dateTime, LanguageUtil languageInterface) {
        Locale currentLocale = Locale.forLanguageTag(languageInterface.getLanguage());
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.of("UTC")).withLocale(currentLocale);
        return zonedDateTime.format(format);
    }
}
