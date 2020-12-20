package com.example.carui;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class Utilities {

    private static Calendar cal = Calendar.getInstance(Locale.getDefault());
    public static int DAY = cal.get(Calendar.DAY_OF_MONTH);
    public static int MONTH = cal.get(Calendar.MONTH);
    public static int YEAR = cal.get(Calendar.YEAR);
    public static int HOUR = cal.get(Calendar.HOUR_OF_DAY);
    public static int MINUTE = cal.get(Calendar.MINUTE);

    public static int LANGUAGE = 2;
    public static boolean AUTOSTATE = true;
    public static String CALLER = null;
    public static int RADIOLIVE = 2;
    public static String[][] STATION_FAVOURITES = {{"+", "+"}, {"+", "+"}, {"+", "+"}};
    public static String[] GPS_FAVOURITES = {"ADD", "ADD", "ADD", "ADD", "ADD"};
    public static int BRIGHTNESS_PROGRESS = 75;
    public static boolean PLAY_STATE = false;
    public static int MUSIC_PROGRESS = 75;

    public static String calcWeather() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int month = cal.get(Calendar.MONTH);
        double temp = (Math.pow((month - 6), 4))/(-25) + 35;
        DecimalFormat df = new DecimalFormat("##.#");
        return df.format(temp) + " C";
    }

    public static String calcTime() {
        return addZeroInBeginning(HOUR) + ":" + addZeroInBeginning(MINUTE);
    }

    public static String calcDate() {
        return addZeroInBeginning(DAY) + " " + (new DateFormatSymbols().getMonths()[MONTH]).substring(0, 3).toUpperCase();
    }

    public static int clampIntToLimits (int x, int min, int max) {
        /*if (x < min) return max;
        else if (x > max) return min;
        return x;*/
        return Math.min(Math.max(x, min), max);
    }

    public static String addZeroInBeginning (int x) {
        return x < 10 ? "0"+x : String.valueOf(x);
    }
}