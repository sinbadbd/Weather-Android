package com.weathertoday.sinbad.weathertoday.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weathertoday.sinbad.weathertoday.Model.WeatherForecastResult;
import com.weathertoday.sinbad.weathertoday.R;
import com.weathertoday.sinbad.weathertoday.common.Common;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecastResult weatherForcastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForcastResult = weatherForecastResult;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int postion) {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForcastResult.list.get(postion).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.img_weather);

        holder.text_date.setText(new StringBuilder(Common.ConvertUnixToDate(weatherForcastResult.list.get(postion).dt)));
        holder.text_description.setText(new StringBuilder(weatherForcastResult.list.get(postion).weather.get(0).getDescription()));
        holder.text_temperature.setText(new StringBuilder(String.valueOf(weatherForcastResult.list.get(postion).main.getTemp())).append("C"));


    }

    @Override
    public int getItemCount() {
        return weatherForcastResult.list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_date, text_temperature, text_description;
        ImageView img_weather;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
            text_date = (TextView) itemView.findViewById(R.id.text_date);
            text_temperature = (TextView) itemView.findViewById(R.id.text_temperature);
            text_description = (TextView) itemView.findViewById(R.id.text_description);
        }
    }
}
