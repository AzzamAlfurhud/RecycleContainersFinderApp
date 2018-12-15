package com.example.max.recyclecontainersfinder;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Marker mPostMarker;
    private String[] values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mMap != null) {
            if (mPostMarker != null) {
                mPostMarker.remove();
                mPostMarker = null;
            }
            // clear needed to update the map after PUT request!
            mMap.clear();
            LoadGeoJsonTask loadGeoJsonTask = new LoadGeoJsonTask(mMap);
            loadGeoJsonTask.execute(Constants.url);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng center = new LatLng(24.717517, 46.624301);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        LoadGeoJsonTask loadGeoJsonTask = new LoadGeoJsonTask(mMap);
        loadGeoJsonTask.execute(Constants.url);
        
        
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                if(mPostMarker == null) {
                    mPostMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("add new recycle"));
                    mPostMarker.showInfoWindow();
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(MapsActivity.this,PostActivity.class);
                            intent.putExtra(PostActivity.LAT_LONG, latLng);
                            MapsActivity.this.startActivity(intent);
                        }
                    });
                }
                else {
                    mPostMarker.remove();
                    mPostMarker = null;
                }

            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
                String tempSnippet = "";
                if(marker.getTitle() != null) {
                    tempSnippet = marker.getSnippet();
                    marker.setSnippet("");
                    marker.showInfoWindow();
                    marker.setSnippet(tempSnippet);
                    values = tempSnippet.split("\\^");
                }
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    // TODO implement putActivity
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(MapsActivity.this,PutActivity.class);
                        intent.putExtra(PutActivity.RECYCLE_ID,values[0]);
                        intent.putExtra(PutActivity.TYPE_ID,values[1]);
                        intent.putExtra(PutActivity.STATUS_ID,values[2]);
                        MapsActivity.this.startActivity(intent);
                    }
                });
                return true;
            }
        });



    }



}
