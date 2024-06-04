package org.astroport.constants;

public enum DockType {
    CORVETTE(1.0),
    DESTROYER(1.5),
    CRUISER(2.0),
    TITAN(2.5),
    COLOSSUS(3.0);

    private final double ratePerHour;

    DockType(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }
}