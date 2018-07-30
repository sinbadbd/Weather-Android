package com.weathertoday.sinbad.weathertoday;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weathertoday.sinbad.weathertoday.Adapter.WeatherForecastAdapter;
import com.weathertoday.sinbad.weathertoday.Model.WeatherForecastResult;
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
public class ForecastFragment extends Fragment {


    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServices;

    TextView text_city_name, text_geo_coords;
    RecyclerView recyclerView;

    static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if (instance == null) {
            instance = new ForecastFragment();
        }
        return instance;
    }


    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mServices = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_forcast, container, false);


        text_city_name = (TextView) itemView.findViewById(R.id.text_city_name);
        text_geo_coords = (TextView) itemView.findViewById(R.id.text_geo_coords);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycle_forecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getForecastWeatherInformation();


        return itemView;
    }

    private void getForecastWeatherInformation() {

        compositeDisposable.add(mServices.getForcastWeather(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metrics")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForcastResult) throws Exception {

                        displayForecastWeather(weatherForcastResult);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        );
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {

        text_city_name.setText(new StringBuilder(weatherForecastResult.city.name));
        text_geo_coords.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));


        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        recyclerView.setAdapter(adapter);
    }


}
