package org.astroport.model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private DockSpot dockSpot;
    private String shipName;
    private double price;
    private LocalDateTime inTime;
    private LocalDateTime outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DockSpot getDockSpot() {
        return dockSpot;
    }

    public void setDockSpot(DockSpot dockSpot) {
        this.dockSpot = dockSpot;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String ShipName) {
        this.shipName = ShipName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
        this.inTime = inTime;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }
}
