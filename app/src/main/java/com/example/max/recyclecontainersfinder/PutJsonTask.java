package com.example.max.recyclecontainersfinder;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutJsonTask extends AsyncTask<JSONObject,Void,Boolean> {

    private String urlString;
    private Activity putActivity;

    public PutJsonTask(String urlString, Activity putActivity){
        this.urlString = urlString;
        this.putActivity = putActivity;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean)
            Toast.makeText(putActivity, "The recycle was successfully updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(putActivity, "An error occured, please try again", Toast.LENGTH_LONG).show();
        putActivity.finish();
    }

    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        return putJSON(urlString,jsonObjects[0]);
    }

    private boolean putJSON(String urlString,JSONObject jsonObject){
        try {
            urlString = urlString+"/"+jsonObject.getString("Id");
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.close();

            return (conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
