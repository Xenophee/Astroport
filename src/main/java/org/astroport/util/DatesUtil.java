package org.astroport.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DatesUtil {

    public static String formatFullDate(LocalDateTime dateTime, LanguageUtil languageInterface) {
        Locale currentLocale = Locale.forLanguageTag(languageInterface.getLanguage());
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.of("UTC")).withLocale(currentLocale);
        return zonedDateTime.format(format);
    }
}
