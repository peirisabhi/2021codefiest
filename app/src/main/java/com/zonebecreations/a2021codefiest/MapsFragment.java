package com.zonebecreations.a2021codefiest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.zonebecreations.a2021codefiest.directionsLib.FetchURL;
import com.zonebecreations.a2021codefiest.pojo.MapDistance;
import com.zonebecreations.a2021codefiest.pojo.MapTimeDuration;

import java.util.Map;

public class MapsFragment extends Fragment {

    private static final int LOCATIO_PERMISSION = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap currentGoogleMap;
    Polyline currentPolyline;

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
            } else {
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

        if (requestCode == LOCATIO_PERMISSION) {
            if (permissions.length > 0) {
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


        if (ActivityCompat.checkSelfPermission(super.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(super.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {

                LatLng customerLocation = null;
                final LatLng[] dropLocation = {null};

                if(location != null){
                    Toast.makeText(MapsFragment.super.getContext(), "Location " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
//
                    customerLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    dropLocation[0] = new LatLng(location.getLatitude(), location.getLongitude());

                    System.out.println(customerLocation);


                    MarkerOptions cuurentLocation = new MarkerOptions().draggable(false).position(customerLocation).title("I'm Heare").icon(getBitmapDesc(getActivity(), R.drawable.ic_tracking));
                    MarkerOptions destinationLocation = new MarkerOptions().draggable(true).position(customerLocation).title("I want to go..").icon(getBitmapDesc(getActivity(), R.drawable.ic_walkto));


                    currentGoogleMap.addMarker(cuurentLocation);
                    currentGoogleMap.addMarker(destinationLocation);
                    currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(customerLocation));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

                    LatLng finalCustomerLocation = customerLocation;
                    currentGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            dropLocation[0] = marker.getPosition();
                            System.out.println(dropLocation[0]);
//                            currentGoogleMap.addPolygon(new PolygonOptions().add(finalCustomerLocation, dropLocation[0]));
//                             = new PolygonOptions().add(finalCustomerLocation, dropLocation[0]);
                            new FetchURL() {
                                @Override
                                public void onTaskDone(Object... values) {
                                    if(currentPolyline != null){
                                        currentPolyline.remove();
                                    }
                                    currentPolyline = currentGoogleMap.addPolyline((PolylineOptions) values[0]);
                                }

                                double distance = 0;


                                @Override
                                public void onTaskDoneDistanse(MapDistance mapDistance) {
                                    System.out.println(mapDistance.getText());
                                    distance = mapDistance.getValue();
                                    TextView distanceTextView = getActivity().findViewById(R.id.distance);
                                    distanceTextView.setText(mapDistance.getText());

                                    if(distance != 0){
                                        double startPrice = 50;
                                        double additionalPricePerKm = 40;

                                        double additionalMeters = distance - 1000;
                                        double additionalKm = additionalMeters/1000;

                                        double additionalPrice = additionalKm * additionalPricePerKm;
//                                        double additionalPrice = (40/1000) * additionalPricePerKm;

                                        System.out.println("addition " + additionalMeters + " | " + additionalPrice);
                                        System.out.println("additional km" + additionalKm);

                                        double estimatedPrice = startPrice + additionalPrice;

                                        TextView priceTextView = getActivity().findViewById(R.id.price);
                                        priceTextView.setText(String.valueOf(estimatedPrice));
                                    }

                                }

                                @Override
                                public void onTaskDoneTimeDuration(MapTimeDuration mapTimeDuration) {
                                    System.out.println(mapTimeDuration.getTime());
                                    TextView durationTextView = getActivity().findViewById(R.id.time);
                                    durationTextView.setText(mapTimeDuration.getTime());
                                }





                            }.execute(getUrl(finalCustomerLocation, dropLocation[0], "driving"), "driving");
                        }
                    });

                }else{
                    System.out.println("location null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsFragment.super.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private BitmapDescriptor getBitmapDesc(FragmentActivity activity, int ic_tracking) {
        Drawable LAYER_1 = ContextCompat.getDrawable(activity,ic_tracking);
        LAYER_1.setBounds(0, 0, LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LAYER_1.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        Log.d("MAP","URL:"+url);
        return url;
    }


}