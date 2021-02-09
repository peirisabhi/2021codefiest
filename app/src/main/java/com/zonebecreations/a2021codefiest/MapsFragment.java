package com.zonebecreations.a2021codefiest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MapsFragment extends Fragment {

    private static final int LOCATIO_PERMISSION = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap currentGoogleMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsFragment.super.getContext());

            currentGoogleMap = googleMap;

            if (ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATIO_PERMISSION);


                return;
            }else{
                updateCurrentLoation();
            }

        }
    };

//    private void requestPermissions(FragmentActivity activity, String[] grantResult, int locatioPermission) {
//        if(locatioPermission == LOCATIO_PERMISSION){
//            if(grantResult.length > 0){
//                updateCurrentLoation();
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATIO_PERMISSION){
            if(permissions.length > 0) {
                updateCurrentLoation();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    void updateCurrentLoation() {


        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Toast.makeText(MapsFragment.super.getContext(), "Location " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();

                    LatLng customerLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    currentGoogleMap.addMarker(new MarkerOptions().position(customerLocation).title("my location"));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(customerLocation));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsFragment.super.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}