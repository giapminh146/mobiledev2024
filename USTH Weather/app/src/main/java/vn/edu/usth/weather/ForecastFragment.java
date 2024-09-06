package vn.edu.usth.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForecastFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
//        view.setBackgroundColor(0x20FF0000);
//        return view;
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setPadding(20, 20, 20, 20);
//        linearLayout.setBackgroundColor(0x20FF0000);
//
//        TextView textView = new TextView(getContext());
//        textView.setText("Thursday");
//        textView.setTextSize(30);
//        textView.setPadding(0, 0, 0, 20);
//
//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageResource(R.drawable.cloudy);
//
//        linearLayout.addView(textView);
//        linearLayout.addView(imageView);
//
//        return linearLayout;
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }
}