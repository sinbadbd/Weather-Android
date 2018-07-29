package com.weathertoday.sinbad.weathertoday.common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID = "f279df88f01232850ddff621c125603d";
    // public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=35&lon=";

    public static Location current_location = null;

    public static String ConvertUnixToDate(int dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm EEE MM yyyyy");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }

    public static String ConvertUnixToHour(int sunrise) {
        Date date = new Date(sunrise * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }
}
