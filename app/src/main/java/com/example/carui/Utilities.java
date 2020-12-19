package com.example.carui;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utilities {

    public static String calcWeather() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int month = cal.get(Calendar.MONTH);
        double temp = (Math.pow((month - 6), 4))/(-25) + 35;
        DecimalFormat df = new DecimalFormat("##.#");
        return df.format(temp) + " C";
    }

    public static String calcTime() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        return addZeroInBeginning(h) + ":" + addZeroInBeginning(m);
    }

    public static String calcDate() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        return addZeroInBeginning(d) + " " + (new DateFormatSymbols().getMonths()[m]).substring(0, 3).toUpperCase();
    }

    public static int clampIntToLimits (int x, int min, int max) {
        if (x < min) return max;
        else if (x > max) return min;
        return x;
        //return Math.min(Math.max(x, min), max);
    }

    public static String addZeroInBeginning (int x) {
        return x < 10 ? "0"+x : String.valueOf(x);
    }
}
