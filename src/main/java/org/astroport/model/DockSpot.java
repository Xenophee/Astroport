package org.astroport.model;

import org.astroport.constants.DockType;

public class DockSpot {
    private int number;
    private DockType dockType;
    private boolean isAvailable;

    public DockSpot(int number, DockType dockType, boolean isAvailable) {
        this.number = number;
        this.dockType = dockType;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return number;
    }

    public void setId(int number) {
        this.number = number;
    }

    public DockType getDockType() {
        return dockType;
    }

    public void setDockType(DockType dockType) {
        this.dockType = dockType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockSpot that = (DockSpot) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
