package org.astroport;

import org.astroport.util.LanguageUtil;
import org.junit.jupiter.api.Test;

import static org.astroport.util.NumbersUtil.doubleToStringWithZero;
import static org.astroport.util.NumbersUtil.roundDecimals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumbersUtilTest {

    @Test
    public void roundDecimals_WhenDecimalIsAboveHalf_ShouldReturnRoundedValue() {
        double result = roundDecimals(1.256, 2);
        assertEquals(1.26, result);
    }

    @Test
    public void roundDecimals_WhenDecimalIsBelowHalf_ShouldReturnRoundedValue() {
        double result = roundDecimals(1.254, 2);
        assertEquals(1.25, result);
    }

    @Test
    public void roundDecimals_WhenNoDecimal_ShouldReturnSameValue() {
        double result = roundDecimals(1, 2);
        assertEquals(1.00, result);
    }

    @Test
    public void doubleToStringWithZero_WhenNoDecimalAndUSLanguage_ShouldReturnFormattedString() {
        LanguageUtil languageInterface = new LanguageUtil();
        languageInterface.setLanguage("us");
        String result = doubleToStringWithZero(1, languageInterface);
        assertEquals("1.00", result);
    }

    @Test
    public void doubleToStringWithZero_WhenDecimalAndUSLanguage_ShouldReturnFormattedString() {
        LanguageUtil languageInterface = new LanguageUtil();
        languageInterface.setLanguage("us");
        String result = doubleToStringWithZero(1.25, languageInterface);
        assertEquals("1.25", result);
    }

    @Test
    public void doubleToStringWithZero_WhenNoDecimalAndFRLanguage_ShouldReturnFormattedString() {
        LanguageUtil languageInterface = new LanguageUtil();
        languageInterface.setLanguage("fr");
        String result = doubleToStringWithZero(1, languageInterface);
        assertEquals("1,00", result);
    }

    @Test
    public void doubleToStringWithZero_WhenDecimalAndFRLanguage_ShouldReturnFormattedString() {
        LanguageUtil languageInterface = new LanguageUtil();
        languageInterface.setLanguage("fr");
        String result = doubleToStringWithZero(1.25, languageInterface);
        assertEquals("1,25", result);
    }
}

