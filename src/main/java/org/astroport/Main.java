package org.astroport;

import org.astroport.service.InteractiveShell;
import org.astroport.util.LanguageUtil;


public class Main {
    private static Application app;
    private static InteractiveShell shell;

    public static void main(String[] args) {
        initializeApp();
    }

    public static void initializeApp() {
        app = new Application();
        shell = app.createInteractiveShell();
        shell.loadInterface();
    }

    public static void resetApp() {
        LanguageUtil.reset();
        app = null;
        shell = null;
        initializeApp();
    }
}