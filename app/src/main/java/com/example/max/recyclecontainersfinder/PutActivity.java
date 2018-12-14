package com.example.max.recyclecontainersfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import org.json.JSONObject;

public class PutActivity extends AppCompatActivity {
    public static final String RECYCLE_ID = "Id";
    public static final String TYPE_ID = "TypeId";
    public static final String STATUS_ID = "StatusId";
    private RadioGroup typeRadioGroup;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        typeRadioGroup = findViewById(R.id.rg_put_type);
        button = findViewById(R.id.btn_put_update);

        //set values (current)

        //get recycle id (using intent)

        //add listener to button to call PutJsonTask
    }
}
