package com.example.lab1113_3getlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextStatus;
    private TextView mTextLocation;

    private LocationManager mLocMgr;
    private int mLocCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextStatus = (TextView) findViewById(R.id.textStatus);
        mTextLocation = (TextView) findViewById(R.id.textLocation);

        mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        showLocationStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!getLocationStatus()) {
            Toast.makeText(this, "위치 서비스를 켜십시오.", Toast.LENGTH_SHORT).show();
            finish();
        }
        String provider = mLocMgr.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocMgr.requestLocationUpdates(provider, 2000, 0, mLocListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocMgr.removeUpdates(mLocListener);
    }

    private void showLocationStatus(){
        boolean net = mLocMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gps = mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String str = String.format(Locale.KOREAN,
                "[현재 상태] Network=%b, GPS=%b", net, gps);
        mTextStatus.setText(str);
    }

    private boolean getLocationStatus(){
        boolean net = mLocMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gps = mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return (net || gps);
    }

    LocationListener mLocListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String str = String.format(Locale.KOREAN,
                    "[현재 위치] 경도 %.4f 위도 %.4f (%d)", location.getLongitude(),
                    location.getLatitude(), ++mLocCnt);
            mTextLocation.setText(str);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
        }
    };
}