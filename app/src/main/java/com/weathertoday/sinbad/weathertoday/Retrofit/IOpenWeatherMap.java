package com.weathertoday.sinbad.weathertoday.Retrofit;

import android.database.Observable;

import com.weathertoday.sinbad.weathertoday.Model.WeatherResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLong(@Query("lon") String lon,
                                                 @Query("lat") String lat,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);


}
