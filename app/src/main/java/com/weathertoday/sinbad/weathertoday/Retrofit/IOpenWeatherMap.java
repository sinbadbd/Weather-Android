package com.weathertoday.sinbad.weathertoday.Retrofit;

import com.weathertoday.sinbad.weathertoday.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLong(@Query("lon") String lon,
                                                  @Query("lat") String lat,
                                                  @Query("appid") String appid,
                                                  @Query("units") String unit);


}
