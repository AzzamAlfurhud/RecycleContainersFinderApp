package com.example.max.recyclecontainersfinder;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PostJsonTask extends AsyncTask<JSONObject,Void,Boolean> {

    private Activity postActivity;
    private String url;

    public PostJsonTask(String url, Activity postActivity){
        this.url = url;
        this.postActivity = postActivity;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean)
            Toast.makeText(postActivity, "The recycle was successfully added", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(postActivity, "An error occured, please try again", Toast.LENGTH_LONG).show();
        postActivity.finish();
    }

    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        return postJSON(url,jsonObjects[0]);
    }

    private boolean postJSON(String urlString, JSONObject jsonObject) {

        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.close();

            return (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED);

//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            httpURLConnection.setDoOutput(true);
//
//            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//            wr.writeBytes("PostData=" + jsonObject.toString());
//            wr.flush();
//            wr.close();

//            String response;

//            InputStream in = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(in);

//            int inputStreamData = inputStreamReader.read();
//            while (inputStreamData != -1) {
//                char current = (char) inputStreamData;
//                inputStreamData = inputStreamReader.read();
//                data += current;
//            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
