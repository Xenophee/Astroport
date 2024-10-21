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


    /**
     * Calculates the fare for a given ticket.
     * The fare is calculated based on the duration of docking and the rate per hour for the dock type.
     * If the docking duration is less than or equal to the maximum hours for free docking, the fare is zero.
     * If a discount is applicable, the fare is reduced by the discount percentage.
     * @param ticket the ticket for which to calculate the fare
     * @param discount a boolean indicating whether a discount is applicable
     */
    public void calculateFare(Ticket ticket, boolean discount) {

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
