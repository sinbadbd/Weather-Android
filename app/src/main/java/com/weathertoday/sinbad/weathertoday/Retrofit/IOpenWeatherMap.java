package com.weathertoday.sinbad.weathertoday.Retrofit;

import com.weathertoday.sinbad.weathertoday.Model.WeatherForecastResult;
import com.weathertoday.sinbad.weathertoday.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLong(@Query("lat") String lat,
                                                  @Query("lon") String lon,
                                                  @Query("appid") String appid,
                                                  @Query("units") String unit);


    @GET("forecast")
    Observable<WeatherForecastResult> getForcastWeather(@Query("lat") String lat,
                                                        @Query("lon") String lon,
                                                        @Query("appid") String appid,
                                                        @Query("units") String unit);


}
