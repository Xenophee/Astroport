package org.astroport.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {

    private String language;
    private ResourceBundle messages;
    private ResourceBundle errors;

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

    public String getLanguage() {
        return language;
    }
}