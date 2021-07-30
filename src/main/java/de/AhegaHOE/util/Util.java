package de.AhegaHOE.util;

public class Util {

    public static String getRemainingTime(Long time) {


        long diff = time - System.currentTimeMillis();

        long seconds = (diff / 1000) % 60;
        long minutes = ((diff / (1000 * 60)) % 60);
        long hours = ((diff / (1000 * 60 * 60)) % 24);
        long days = ((diff / (1000 * 60 * 60 * 24)) % 7);
        long weeks = (diff / (1000 * 60 * 60 * 24 * 7));

        if (weeks >= 1) {
            return weeks + "w " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (weeks == 0) {
            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (days == 0) {
            if (hours == 1 && minutes == 1 && seconds == 1) {
                return hours + " Stunde " + minutes + " Minute " + seconds + " Sekunde";
            } else if (minutes == 1 && seconds == 1) {
                return hours + " Stunden " + minutes + " Minute " + seconds + " Sekunde";
            } else if (seconds == 1) {
                return hours + " Stunden " + minutes + " Minuten " + seconds + " Sekunde";
            } else {
                return hours + " Stunden " + minutes + " Minuten " + seconds + " Sekunden";
            }
        } else if (hours == 0) {
            if (minutes == 1 && seconds == 1) {
                return minutes + " Minute " + seconds + " Sekunde";
            } else if (seconds == 1) {
                return minutes + " Minuten " + seconds + " Sekunde";
            } else {
                return minutes + " Minuten " + seconds + " Sekunden";
            }
        } else if (minutes == 0) {

            if (seconds == 1) {
                return seconds + "Sekunde";
            } else {
                return seconds + "Sekunden";
            }
        }

        return null;
    }


}
