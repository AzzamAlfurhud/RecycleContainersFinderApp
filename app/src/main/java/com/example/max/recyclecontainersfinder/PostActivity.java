package com.example.max.recyclecontainersfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    public static final String LAT_LONG = "LatLng";

    private RadioGroup typeRadioGroup, statusRadioGroup;

    private Button button;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        typeRadioGroup = findViewById(R.id.rg_type);
        statusRadioGroup = findViewById(R.id.rg_status);

        // Set defaults
        typeRadioGroup.check(R.id.rb_type_general);
        statusRadioGroup.check(R.id.rb_status_empty);

        button = findViewById(R.id.btn_submit);
        latLng = (LatLng) getIntent().getParcelableExtra(LAT_LONG);
        Log.i("PostActivity", "onCreate: " + latLng);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton checkedTypeRadioButton = findViewById(typeRadioGroup.getCheckedRadioButtonId());
                RadioButton checkedStatusRadioButton = findViewById(statusRadioGroup.getCheckedRadioButtonId());

                PostJsonTask postJsonTask = new PostJsonTask(Constants.url, PostActivity.this);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Longitude", latLng.longitude);
                    jsonObject.put("Latitude", latLng.latitude);
                    jsonObject.put("TypeId", checkedTypeRadioButton.getText().toString());
                    jsonObject.put("StatusId", checkedStatusRadioButton.getText().toString());
                    postJsonTask.execute(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


}
