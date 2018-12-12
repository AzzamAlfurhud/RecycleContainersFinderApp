package com.example.max.recyclecontainersfinder;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoadGeoJsonTask extends AsyncTask<String, Void, JSONObject>{


    private static final String TAG = "LoadGeoJsonTask";

    private GoogleMap mMap;

    LoadGeoJsonTask(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            JSONArray featureS = (JSONArray) jsonObject.get("features");
            JSONObject feature = featureS.getJSONObject(0);
            JSONObject properties = (JSONObject) feature.get("properties");
            Log.i(TAG, "onPostExecute: " + properties.getString("recycleType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GeoJsonLayer layer1 = null;
        layer1 = new GeoJsonLayer(mMap,jsonObject);

        layer1.addLayerToMap();

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


}
