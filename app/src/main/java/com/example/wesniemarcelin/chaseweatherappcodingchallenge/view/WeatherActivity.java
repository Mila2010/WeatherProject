package com.example.wesniemarcelin.chaseweatherappcodingchallenge.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wesniemarcelin.chaseweatherappcodingchallenge.R;
import com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants;
import com.example.wesniemarcelin.chaseweatherappcodingchallenge.presenter.SearchWeatherPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.CITY_NAME;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.CURRENT_TEMP;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.ICON;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.LAST_CITY_SEARCHED;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.NO_CITY_SEARCHED;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WEATHER_ACTIVITY;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WEATHER_DESCRIPTION;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WEATHER_TYPE;
import static com.example.wesniemarcelin.chaseweatherappcodingchallenge.model.Constants.WIND_SPEED;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, WeatherView {

    @BindView(R.id.submit_city)
    Button mSubmitCityBtn;
    @BindView(R.id.city_editText)
    EditText mCityEditText;
    SearchWeatherPresenter mSearchWeatherPresenter;
    String mCityName = "";



    SharedPreferences mSaveLastCitySearched = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        mSaveLastCitySearched = getSharedPreferences(WEATHER_ACTIVITY, MODE_PRIVATE);
        ButterKnife.bind(this);

        mSubmitCityBtn.setOnClickListener(this);
        mSearchWeatherPresenter = new SearchWeatherPresenter(this);

        if (!mSaveLastCitySearched.getString(LAST_CITY_SEARCHED, NO_CITY_SEARCHED).equalsIgnoreCase(NO_CITY_SEARCHED)) {
            mSearchWeatherPresenter.fetchWeather(mSaveLastCitySearched.getString(LAST_CITY_SEARCHED, NO_CITY_SEARCHED));

        }

    }

    @Override
    public void onClick(View view) {

        if (!mCityEditText.getText().toString().isEmpty()) {
            mCityName = mCityEditText.getText().toString();
            mSaveLastCitySearched.edit().putString(LAST_CITY_SEARCHED, mCityName).apply();

        } else {
            Toast.makeText(getApplicationContext(), "City hasn't been specified", Toast.LENGTH_LONG).show();
        }

        mSearchWeatherPresenter.fetchWeather(mCityName);

    }

    @Override
    public void showWeather(ArrayMap<String, String> weatherDetails) {

        Bundle bundle = new Bundle();

        // Create new fragment and transaction to allow user to view the weather for the location provided
        Fragment newFragment = new ViewWeatherFragment();
        bundle.putString(ICON, weatherDetails.get(ICON));
        bundle.putString(WEATHER_DESCRIPTION, weatherDetails.get(WEATHER_DESCRIPTION));
        bundle.putString(WEATHER_TYPE, weatherDetails.get(WEATHER_TYPE));
        bundle.putString(WIND_SPEED, weatherDetails.get(WIND_SPEED));
        bundle.putString(CURRENT_TEMP, weatherDetails.get(CURRENT_TEMP));
        bundle.putString(CITY_NAME, weatherDetails.get(CITY_NAME));
        newFragment.setArguments(bundle);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }


}
