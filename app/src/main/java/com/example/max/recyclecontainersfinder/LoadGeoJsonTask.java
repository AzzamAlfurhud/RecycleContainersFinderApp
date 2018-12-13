package com.example.max.recyclecontainersfinder;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoadGeoJsonTask extends AsyncTask<String, Void, JSONObject> {


    private static final String TAG = "LoadGeoJsonTask";
    public Marker mMarker = null;
    String s = "";

    private GoogleMap mMap;

    LoadGeoJsonTask(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        JSONArray featureS,coordinates;
        JSONObject feature,properties,geometry;
        String type = "";
        String status = "";
        LatLng latLng = null;
        try {
            featureS = (JSONArray) jsonObject.get("features");
            feature = featureS.getJSONObject(0);
            properties = (JSONObject) feature.get("properties");
            geometry = (JSONObject) feature.get("geometry");
            coordinates = (JSONArray) geometry.get("coordinates");
            latLng = new LatLng(coordinates.getDouble(1),coordinates.getDouble(0));
//            Log.i(TAG, "onPostExecute: " + );
            type = properties.getString("recycleType");
            status = properties.getString("recycleStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GeoJsonLayer layer1 = null;

        layer1 = new GeoJsonLayer(mMap,jsonObject);
        layer1.addLayerToMap();
        Iterable<GeoJsonFeature> GF = layer1.getFeatures();
        int i = 0;
        for (GeoJsonFeature g: GF
             ) {
            try {
                featureS = (JSONArray) jsonObject.get("features");
                feature = featureS.getJSONObject(i++);
                properties = (JSONObject) feature.get("properties");
                geometry = (JSONObject) feature.get("geometry");
                coordinates = (JSONArray) geometry.get("coordinates");
                latLng = new LatLng(coordinates.getDouble(1),coordinates.getDouble(0));
//            Log.i(TAG, "onPostExecute: " + );
                type = properties.getString("recycleType");
                status = properties.getString("recycleStatus");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MarkerOptions mMO = g.getMarkerOptions();
            mMap.addMarker(mMO.position(latLng).title("Type: "+type+" Status: "+status));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });

        }



    }

    private JSONObject readJSON(String urlString) {
        JSONObject jsonObject = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");
            String data = scanner.next();
            jsonObject = new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return readJSON(strings[0]);
    }

    public void showInfo(){

    }



}
