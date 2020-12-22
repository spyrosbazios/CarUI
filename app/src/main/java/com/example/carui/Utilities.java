package com.example.carui;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utilities {

    private static Calendar cal = Calendar.getInstance(Locale.getDefault());
    public static int DAY = cal.get(Calendar.DAY_OF_MONTH);
    public static int MONTH = cal.get(Calendar.MONTH);
    public static int YEAR = cal.get(Calendar.YEAR);
    public static int HOUR = cal.get(Calendar.HOUR_OF_DAY);
    public static int MINUTE = cal.get(Calendar.MINUTE);

    public static final String[] LANGUAGE_LIST = {"English", "Greek", "Spanish"};
    public static int LANGUAGE = 0;
    public static boolean AUTO_STATE = true;
    public static String CALLER = null;
    public static CallingActivity.CALLSTATE CALL_STATE = CallingActivity.CALLSTATE.ANSWER;
    public static int RADIO_LIVE = 2;
    public static String[][] STATION_FAVOURITES = {{"+", "+"}, {"+", "+"}, {"+", "+"}};
    public static String[] GPS_FAVOURITES = {"ADD", "ADD", "ADD", "ADD", "ADD"};
    public static int BRIGHTNESS_PROGRESS = 75;
    public static boolean PLAY_STATE = true;
    public static int MUSIC_PROGRESS = 75;
    public static ConstraintLayout.LayoutParams BUBBLE_POSITION = null;

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
        return Math.min(Math.max(x, min), max);
    }

    public static String addZeroInBeginning (int x) {
        return x < 10 ? "0"+x : String.valueOf(x);
    }

    private static long tms;
    public static void startTimer() {
        final long ct = System.currentTimeMillis();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(CALLER != null) {
                    tms = System.currentTimeMillis() - ct;
                    try {Thread.sleep(1000);}
                    catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        });
        t.start();
    }
    public static long getCallDuration() {return tms;}
}