package org.astroport;

import org.astroport.service.InteractiveShell;


public class Main {

    public static void main(String[] args) {
        initializeApp();
    }

    public static void initializeApp() {
        InteractiveShell app = AppConfig.initializeConfig();
        app.loadInterface();
    }

    public static void resetApp() {
        initializeApp();
    }
}