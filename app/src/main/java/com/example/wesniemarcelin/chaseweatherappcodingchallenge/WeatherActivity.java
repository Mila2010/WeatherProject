package com.example.wesniemarcelin.chaseweatherappcodingchallenge;

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

import com.example.wesniemarcelin.chaseweatherappcodingchallenge.fragments.SearchWeatherPresenter;
import com.example.wesniemarcelin.chaseweatherappcodingchallenge.fragments.ViewWeatherFragment;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener,WeatherView {

    Fragment mContent;
    Button submitCityBtn;
    EditText cityEditText;
    ImageView viewIcon;
    private static String TAG = "YOOOO";
    SearchWeatherPresenter mSearchWeatherPresenter;
    String mCityName="";


    String WEATHER_ACTIVITY="com.example.wesniemarcelin.chaseweatherappcodingchallenge";
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        prefs = getSharedPreferences(WEATHER_ACTIVITY, MODE_PRIVATE);
        //mSearchWeatherFragment = new SearchWeatherPresenter();
        submitCityBtn = (Button) findViewById(R.id.submit_city);
        cityEditText = (EditText) findViewById(R.id.city_editText);

        viewIcon = (ImageView) findViewById(R.id.view_icon);

        submitCityBtn.setOnClickListener(this);
        mSearchWeatherPresenter=new SearchWeatherPresenter(this);

        if(savedInstanceState != null){
            Log.d("onSaveInstanceState", "onCreate: of Activity");

            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"SearchFragment");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

       // getSupportFragmentManager().putFragment(outState,"searchFragment",mContent);
//        Log.d("onSaveInstanceState", "onSaveInstanceState: of Activity");
//        outState.putString("");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (prefs.getString("cityName", "defaultStringIfNothingFound").equalsIgnoreCase("defaultStringIfNothingFound")) {
//            // Do first run stuff here then set 'firstrun' as false
//            // using the following line to edit/commit prefs
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.activity_main_fragment, mSearchWeatherFragment)
//                    .commit();
//
//            Log.d("ON RESUME: ", "This is the first time");
//            //prefs.edit().putBoolean("firstrun", false).apply();
//        }
//        else {
//            Log.d("ON RESUME: ", "THis is not the first time");
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .add(R.id.activity_main_fragment, new ViewSavedWeatherFragment())
//                    .commit();
//
//        }

    }

    @Override
    public void onClick(View view) {

//        if (savedInstanceStateExists) {
//                    Log.d(TAG, "onClick worked! for " + cityName);
//                    sharedpreferences.edit().putString("cityName", cityName).apply();
//                    bundle.putString("location", cityName);
//                    fetchWeather(cityName);
//
//                } else{
                Toast.makeText(getContext(), "Please enter a valid input", Toast.LENGTH_LONG).show();
if(!cityEditText.getText().toString().isEmpty()){
                mCityName = cityEditText.getText().toString();

}else
{
    Toast.makeText(getContext(),"City hasn't been specified",Toast.LENGTH_LONG).show();
}

       mSearchWeatherPresenter.fetchWeather(mCityName);

    }

    @Override
    public void showWeather(ArrayMap<String,String> weatherDetails) {

        Bundle bundle = new Bundle();

            // Create new fragment and transaction to allow user to view the weather for the location provided
            Log.e(TAG, "Viewing Weather: ");
            Fragment newFragment = new ViewWeatherFragment();
            bundle.putString("icon", weatherDetails.get("url"));
            bundle.putString("description", weatherDetails.get("weatherDescription"));
            bundle.putString("main", weatherDetails.get("mainWeatherType"));
            bundle.putString("windSpeed", weatherDetails.get("mWindSpeed"));
            bundle.putString("currentTemp", weatherDetails.get("currentTemp"));
            newFragment.setArguments(bundle);


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();


    }

    @Override
    public Context getContext() {
        return getApplicationContext() ;
    }
}
