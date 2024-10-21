package org.astroport.util;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class provides utility methods for handling languages.
 * It includes methods for setting the language, and getting messages and errors in the current language.
 */
public class LanguageUtil {

    private String language;
    private ResourceBundle messages;
    private ResourceBundle errors;


    /**
     * Constructor for the LanguageUtil class.
     * It sets the language and loads the resource bundles for messages and errors in the current language.
     * @param language the language to set
     */
    public LanguageUtil(String language) {
        this.language = language;
        this.messages = ResourceBundle.getBundle("messages", Locale.forLanguageTag(language));
        this.errors = ResourceBundle.getBundle("errors", Locale.forLanguageTag(language));
    }


    /**
     * Sets the language and reloads the resource bundles for messages and errors in the new language.
     * @param language the new language to set
     */
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