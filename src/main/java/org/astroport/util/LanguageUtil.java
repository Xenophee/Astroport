package org.astroport.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {

    private String language;
    private static final LanguageUtil INSTANCE = new LanguageUtil();

    private LanguageUtil() {}

    public static LanguageUtil getInstance() {
        return INSTANCE;
    }

    public ResourceBundle getMessagesBundle() {
        return ResourceBundle.getBundle("messages", Locale.forLanguageTag(this.language));
    }

    public ResourceBundle getErrorsBundle() {
        return ResourceBundle.getBundle("errors", Locale.forLanguageTag(this.language));
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}