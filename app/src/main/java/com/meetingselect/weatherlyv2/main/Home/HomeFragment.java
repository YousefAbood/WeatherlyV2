package com.meetingselect.weatherlyv2.main.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetingselect.weatherlyv2.HelperClasses.GPSLocation;
import com.meetingselect.weatherlyv2.R;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getWeatherConditions() {


    }

    // Location
    public String getLocation() {
        GPSLocation gpsLocation = new GPSLocation(getActivity());
        if (gpsLocation.canGetLocation()) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            String latitudeS = String.valueOf(latitude);
            String longitudeS = String.valueOf(longitude);
            String latlong = latitudeS + "," + longitudeS;
            Log.d(TAG, "getLocation: " + latlong);
            return latlong;
        } else {
            gpsLocation.showSettingsAlert();
            return "nothing";
        }
    }
}