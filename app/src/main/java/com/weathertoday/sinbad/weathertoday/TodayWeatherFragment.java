package com.weathertoday.sinbad.weathertoday;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weathertoday.sinbad.weathertoday.Model.WeatherResult;
import com.weathertoday.sinbad.weathertoday.Retrofit.IOpenWeatherMap;
import com.weathertoday.sinbad.weathertoday.Retrofit.RetrofitClient;
import com.weathertoday.sinbad.weathertoday.common.Common;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView text_city_name, text_temperature, text_description, text_datetime, text_wind, text_pressure, text_humidity, text_sunrise, text_sunset, text_geo_coords;

    LinearLayout weather_parent;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServices;


    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null) {
            instance = new TodayWeatherFragment();
        }
        return instance;
    }


    public TodayWeatherFragment() {
        // Required empty public constructor

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mServices = retrofit.create(IOpenWeatherMap.class);
        //  mServices.getWeatherByLatLong(String.valueOf(Common.current_location.getLatitude()), String.valueOf(Common.current_location.getLongitude()), Common.APP_ID, "metrics");
        ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);

        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        text_city_name = (TextView) itemView.findViewById(R.id.text_city_name);
        text_description = (TextView) itemView.findViewById(R.id.text_description);
        text_datetime = (TextView) itemView.findViewById(R.id.text_datetime);
        text_humidity = (TextView) itemView.findViewById(R.id.text_humidity);
        text_pressure = (TextView) itemView.findViewById(R.id.text_pressure);
        text_sunrise = (TextView) itemView.findViewById(R.id.text_sunrise);
        text_sunset = (TextView) itemView.findViewById(R.id.text_sunset);
        text_temperature = (TextView) itemView.findViewById(R.id.text_temperature);
        text_wind = (TextView) itemView.findViewById(R.id.text_wind);
        text_geo_coords = (TextView) itemView.findViewById(R.id.text_geo_coords);

        weather_parent = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);


        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {

        compositeDisposable.add(mServices.getWeatherByLatLong(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID, "metrics")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                        //Load image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(img_weather);
                        text_city_name.setText(weatherResult.getName());
                        text_description.setText(new StringBuilder("Weather in ").append(weatherResult.getName()).toString());
                        text_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                        text_datetime.setText(Common.ConvertUnixToDate(weatherResult.getDt()));
                        text_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                        text_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                        text_sunrise.setText(Common.ConvertUnixToHour(weatherResult.getSys().getSunrise()));
                        text_sunset.setText(Common.ConvertUnixToHour(weatherResult.getSys().getSunset()));
                        text_geo_coords.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());

                        //Display panel
                        weather_parent.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        );
    }

}
