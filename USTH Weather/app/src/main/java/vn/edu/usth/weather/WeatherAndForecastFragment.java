package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WeatherAndForecastFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);

        getChildFragmentManager().beginTransaction().add(R.id.weather_fragment, new WeatherFragment()).commit();
        getChildFragmentManager().beginTransaction().add(R.id.forecast_fragment, new ForecastFragment()).commit();

        return view;
    }
}