package org.astroport.util;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LanguageUtil {

    private static LanguageUtil instance;

    private String language;
    public ResourceBundle messages;
    public ResourceBundle errors;

    private LanguageUtil() {}

    public static LanguageUtil getInstance() {
        if (instance == null) {
            instance = new LanguageUtil();
        }
        return instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        this.messages = ResourceBundle.getBundle("messages", Locale.forLanguageTag(language));
        this.errors = ResourceBundle.getBundle("errors", Locale.forLanguageTag(language));
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public ResourceBundle getErrors() {
        return errors;
    }

    public static void reset() {
        instance = null;
    }
}