package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i(TAG, "Create");

//        WeatherFragment secondFragment = new WeatherFragment();
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.main, secondFragment).commit();
//
//        ForecastFragment firstFragment = new ForecastFragment();
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.main, firstFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroy");
    }

}