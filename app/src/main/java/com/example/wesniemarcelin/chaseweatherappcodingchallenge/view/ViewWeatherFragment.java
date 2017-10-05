package com.example.wesniemarcelin.chaseweatherappcodingchallenge.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wesniemarcelin.chaseweatherappcodingchallenge.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.CITY_NAME;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.CURRENT_TEMP;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.ICON;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WEATHER_DESCRIPTION;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WIND_SPEED;


/**
 * Created by wesniemarcelin on 9/30/17.
 */

public class ViewWeatherFragment extends Fragment {
    @BindView(R.id.icon_image)
    ImageView mWeatherIcon;
    @BindView(R.id.weather_wind)
    TextView mWindSpeed;
    @BindView(R.id.weather_description)
    TextView mWeatherDescription;
    @BindView(R.id.weather_temp)
    TextView mCurrentTemp;
    @BindView(R.id.city_name)
    TextView mCityName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mWeatherViewroot = inflater.inflate(R.layout.view_weather_fragment, container, false);
        ButterKnife.bind(this, mWeatherViewroot);

        showWeather();

        return mWeatherViewroot;
    }

    private void showWeather() {

        String myIconUrl = "icon";
        String weatherDescriptionText = "Unknown description";
        String weatherWindSpeed = "Unknown speed";
        String currentTemperature = "Unknown temperature";
        String cityName = "Unknown City";

        if (!getArguments().isEmpty()) {
            Bundle bundle = this.getArguments();
            myIconUrl = bundle.getString(ICON);
            weatherDescriptionText = bundle.getString(WEATHER_DESCRIPTION);
            weatherWindSpeed = bundle.getString(WIND_SPEED);
            currentTemperature = bundle.getString(CURRENT_TEMP);
            cityName = bundle.getString(CITY_NAME);

        }

        mWeatherDescription.setText(getString(R.string.weather, weatherDescriptionText));
        mWindSpeed.setText(getString(R.string.wind, weatherWindSpeed));
        mCurrentTemp.setText(getString(R.string.temperature, currentTemperature));
        mCityName.setText(getString(R.string.city_name,cityName));
        Picasso.with(getContext())
                .load(myIconUrl)
                .into(mWeatherIcon);

    }


}
