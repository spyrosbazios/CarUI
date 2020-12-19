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
        String hours = h < 10 ? "0"+h : String.valueOf(h);
        int m = cal.get(Calendar.MINUTE);
        String minutes = m < 10 ? "0"+m : String.valueOf(m);
        return hours + ":" + minutes;
    }

    public static String calcDate() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        String day = d < 10 ? "0"+d : String.valueOf(d);
        return day + " " + (new DateFormatSymbols().getMonths()[month]).substring(0, 3).toUpperCase();
    }
}
