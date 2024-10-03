package vn.edu.usth.weather;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        MaterialToolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        // Link the TabLayout and the ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("Hanoi, Vietnam");
                        break;
                    case 1:
                        tab.setText("Paris, France");
                        break;
                    case 2:
                        tab.setText("Toulouse, France");
                        break;
                }
            }
        }).attach();

        // Start the AsyncTask to simulate refreshing
        new RefreshTask(this).execute();
//        Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
//        simulateNetworkRequest();
        copyMP3ToMediaStore();

        Log.i(TAG, "Create");
    }

    private static class RefreshTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<WeatherActivity> activityReference;

        // Constructor
        RefreshTask(WeatherActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            // Get a strong reference to the activity (if it exists)
            WeatherActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return; // Activity no longer exists
            }
            // Show the "Refreshing..." toast
            Toast.makeText(activity, "Refreshing...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Simulate the background task (e.g., refreshing)
            try {
                Thread.sleep(10000); // 10-second delay simulating a task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Get a strong reference to the activity (if it exists)
            WeatherActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return; // Activity no longer exists
            }
            // Show "Refresh completed!" toast
            Toast.makeText(activity, "Refresh completed!", Toast.LENGTH_SHORT).show();
        }
    }
    // MP3
    private void copyMP3ToMediaStore() {
        InputStream inputStream = getResources().openRawResource(R.raw.big_banana_song);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "big_banana_song.mp3");
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mpeg");
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC);

        // Insert the audio file into MediaStore
        Uri uri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                Log.d(TAG, "MP3 file copied to MediaStore: " + uri.toString());
                playMP3(uri);  // Play after copying
            } catch (IOException e) {
                Log.e(TAG, "Failed to copy MP3 file to MediaStore", e);
            }
        } else {
            Log.e(TAG, "Failed to insert MP3 file into MediaStore");
        }
    }

    private void playMP3(Uri uri) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.d(TAG, "Playing MP3 from MediaStore: " + uri.toString());
        } catch (IOException e) {
            Log.e(TAG, "Failed to play MP3 file", e);
        }
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            Log.i(TAG, "Refresh button clicked");
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.settings) {
            Intent intent = new Intent(WeatherActivity.this, PrefActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void simulateNetworkRequest() {
//        final Handler handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                // Execute in the main thread
//                String content = msg.getData().getString("server_response");
//                Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
//            }
//        };
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // Run in worker thread
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                Bundle bundle = new Bundle();
//                bundle.putString("server_response", "Refresh completed!");
//
//                // notify main thread
//                Message msg = new Message();
//                msg.setData(bundle);
//                handler.sendMessage(msg);
//            }
//        });
//        t.start();
//    }


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