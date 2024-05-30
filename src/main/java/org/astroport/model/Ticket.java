package org.astroport.model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private DockSpot dockSpot;
    private String shipRegNumber;
    private double price;
    private LocalDateTime inTime;
    private LocalDateTime outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DockSpot getParkingSpot() {
        return dockSpot;
    }

    public void setParkingSpot(DockSpot dockSpot) {
        this.dockSpot = dockSpot;
    }

    public String getVehicleRegNumber() {
        return shipRegNumber;
    }

    public void setVehicleRegNumber(String shipRegNumber) {
        this.shipRegNumber = shipRegNumber;
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
