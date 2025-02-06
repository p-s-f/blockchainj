package com.kbtech.blockchain.util;

public class TimeUtil {

    public static String formatTime(double seconds) {
        // Round up the seconds to the nearest whole number
        int roundedSeconds = (int) Math.ceil(seconds);

        if (roundedSeconds < 60) {
            return roundedSeconds + " second" + (roundedSeconds == 1 ? "" : "s");
        } else if (roundedSeconds < 3600) {
            int minutes = roundedSeconds / 60;
            return minutes + " minute" + (minutes == 1 ? "" : "s");
        } else {
            int hours = roundedSeconds / 3600;
            int remainingMinutes = (roundedSeconds % 3600) / 60;
            return hours + " hour" + (hours == 1 ? "" : "s") +
                    (remainingMinutes > 0 ? " " + remainingMinutes + " minute" + (remainingMinutes == 1 ? "" : "s") : "");
        }
    }
}
