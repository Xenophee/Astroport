package org.astroport.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumbersUtil {

    public static double roundDecimals(double number, int scale) {
        return BigDecimal.valueOf(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
