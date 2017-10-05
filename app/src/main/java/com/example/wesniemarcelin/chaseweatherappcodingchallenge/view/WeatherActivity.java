package com.example.wesniemarcelin.chaseweatherappcodingchallenge.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wesniemarcelin.chaseweatherappcodingchallenge.R;
import com.example.wesniemarcelin.chaseweatherappcodingchallenge.presenter.SearchWeatherPresenter;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, WeatherView {

    Button submitCityBtn;
    EditText cityEditText;
    ImageView viewIcon;
    private static String TAG = "YOOOO";
    SearchWeatherPresenter mSearchWeatherPresenter;
    String mCityName = "";


    final String WEATHER_ACTIVITY = "com.example.wesniemarcelin.chaseweatherappcodingchallenge";
    final String LAST_CITY_SEARCHED = "cityName";
    final String NO_CITY_SEARCHED = "noCityName";

    SharedPreferences mSaveLastCitySearched = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        mSaveLastCitySearched = getSharedPreferences(WEATHER_ACTIVITY, MODE_PRIVATE);
        submitCityBtn = (Button) findViewById(R.id.submit_city);
        cityEditText = (EditText) findViewById(R.id.city_editText);

        viewIcon = (ImageView) findViewById(R.id.view_icon);

        submitCityBtn.setOnClickListener(this);
        mSearchWeatherPresenter = new SearchWeatherPresenter(this);

        if (!mSaveLastCitySearched.getString(LAST_CITY_SEARCHED, NO_CITY_SEARCHED).equalsIgnoreCase(NO_CITY_SEARCHED)) {
            Log.d("onSaveInstanceState", "onCreate: of Activity");

            mSearchWeatherPresenter.fetchWeather(mSaveLastCitySearched.getString(LAST_CITY_SEARCHED, NO_CITY_SEARCHED));


        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }


    @Override
    public void onClick(View view) {

        Toast.makeText(getContext(), "Please enter a valid input", Toast.LENGTH_LONG).show();
        if (!cityEditText.getText().toString().isEmpty()) {
            mCityName = cityEditText.getText().toString();
            mSaveLastCitySearched.edit().putString(LAST_CITY_SEARCHED, mCityName).apply();

        } else {
            Toast.makeText(getContext(), "City hasn't been specified", Toast.LENGTH_LONG).show();
        }

        mSearchWeatherPresenter.fetchWeather(mCityName);

    }

    @Override
    public void showWeather(ArrayMap<String, String> weatherDetails) {

        Bundle bundle = new Bundle();

        // Create new fragment and transaction to allow user to view the weather for the location provided
        Log.e(TAG, "Viewing Weather: ");
        Fragment newFragment = new ViewWeatherFragment();
        bundle.putString("icon", weatherDetails.get("url"));
        bundle.putString("description", weatherDetails.get("weatherDescription"));
        bundle.putString("main", weatherDetails.get("mainWeatherType"));
        bundle.putString("windSpeed", weatherDetails.get("mWindSpeed"));
        bundle.putString("currentTemp", weatherDetails.get("currentTemp"));
        bundle.putString("cityName", weatherDetails.get("cityName"));
        newFragment.setArguments(bundle);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public Context getContext() {
        return getApplicationContext();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}