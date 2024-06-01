package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.constants.Fare;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.astroport.constants.Discount.*;
import static org.astroport.util.NumbersUtil.roundDecimals;

public class FareCalculatorService {

    private final LanguageUtil languageInterface;
    private final ResourceBundle messages;
    private final ResourceBundle errors;

    public FareCalculatorService() {
        this.languageInterface = AppConfig.getLanguageInterface();
        this.messages = languageInterface.getMessages();
        this.errors = languageInterface.getErrors();
    }

    public void calculateFare(Ticket ticket, boolean discount) {
        if (ticket.getOutTime() == null || ticket.getOutTime().isBefore(ticket.getInTime())) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        LocalDateTime inTime = ticket.getInTime();
        LocalDateTime outTime = ticket.getOutTime();

        long durationInMinutes = Duration.between(inTime, outTime).toMinutes();
        double durationInHours = roundDecimals(durationInMinutes / 60.0, 2);

        if (durationInHours <= MAX_HOURS_FREE_PARKING) return;

        double price;

        switch (ticket.getDockSpot().getDockType()) {
            case CORVETTE: {
                price = durationInHours * Fare.CORVETTE_RATE_PER_HOUR;
                break;
            }
            case DESTROYER: {
                price =  durationInHours * Fare.DESTROYER_RATE_PER_HOUR;
                break;
            }
            case CRUISER: {
                price =  durationInHours * Fare.CRUISER_RATE_PER_HOUR;
                break;
            }
            case TITAN: {
                price =  durationInHours * Fare.TITAN_RATE_PER_HOUR;
                break;
            }
            case COLOSSUS: {
                price =  durationInHours * Fare.COLOSSUS_RATE_PER_HOUR;
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }

        if (discount) price *= RECURRING_USER_DISCOUNT_PERCENTAGE;

        ticket.setPrice(roundDecimals(price, 2));
    }
}
