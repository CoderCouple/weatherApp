package com.android.aaditya.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.aaditya.weather.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements BaseActivity.PermissionCallback {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.responseView)
    TextView responseView;
    private ForecastPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new ForecastPresenterImpl();
        responseView.setText("Will show size");
    }

    public void getLatLon(View view){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,this);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Timber.d(String.valueOf(location.getLatitude()));
    }
    private Handler mHandler = null;

    private HandlerThread mHandlerThread = null;

    public void get10days(View view) throws IOException {
        StringBuilder jsonResults = new StringBuilder();
            mHandlerThread = new HandlerThread("HandlerThread");
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());

        mHandler.post(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://api.timezonedb.com/v2/get-time-zone?key=AZB2TC5HOU68&format=json&lat=37.3382082&lng=-121.8863286&by=position");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Timber.d(in.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        });

    }

    public void showListView(View view) {
        startActivity(CityListActivity.class, null);
    }

    @Override
    public void onPermissionGranted(String[] grantedPermissions) {

    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {

    }

    @Override
    public void onPermissionBlocked(String[] blockedPermissions) {

    }
}
