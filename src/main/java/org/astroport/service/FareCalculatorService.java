package org.astroport.service;

import org.astroport.AppConfig;
import org.astroport.model.Ticket;
import org.astroport.util.LanguageUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static org.astroport.constants.Discount.*;
import static org.astroport.util.NumbersUtil.roundDecimals;

public class FareCalculatorService {

    private final LanguageUtil languageInterface = AppConfig.getLanguageInterface();
    private final ResourceBundle messages = languageInterface.getMessages();
    private final ResourceBundle errors = languageInterface.getErrors();


    public FareCalculatorService() {}

    public void calculateFare(Ticket ticket, boolean discount) {

        if (ticket.getOutTime() == null || ticket.getOutTime().isBefore(ticket.getInTime())) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        LocalDateTime inTime = ticket.getInTime();
        LocalDateTime outTime = ticket.getOutTime();

        long durationInMinutes = Duration.between(inTime, outTime).toMinutes();
        double durationInHours = roundDecimals(durationInMinutes / 60.0, 2);

        if (durationInHours <= MAX_HOURS_FREE_PARKING) return;

        double ratePerHour = ticket.getDockSpot().getDockType().getRatePerHour();

        double price = durationInHours * ratePerHour;

        if (discount) price *= RECURRING_USER_DISCOUNT_PERCENTAGE;

        ticket.setPrice(roundDecimals(price, 2));
    }
}
