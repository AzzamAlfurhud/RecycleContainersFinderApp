package com.example.max.recyclecontainersfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

public class PutActivity extends AppCompatActivity {
    public static final String RECYCLE_ID = "Id";
    public static final String TYPE_ID = "TypeId";
    public static final String STATUS_ID = "StatusId";
    private static final String TAG = "PutActivity";

    private RadioGroup typeRadioGroup,statusRadioGroup;
    private Button button;
    private String type,status,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        typeRadioGroup = findViewById(R.id.rg_put_type);
        statusRadioGroup = findViewById(R.id.rg_put_status);
        button = findViewById(R.id.btn_put_update);

        id = (String) getIntent().getStringExtra(RECYCLE_ID);
        type = (String) getIntent().getStringExtra(TYPE_ID);
        status = (String) getIntent().getStringExtra(STATUS_ID);

        //set default values (current Marker)
        //type
        if(type.equalsIgnoreCase("General")){
            typeRadioGroup.check(R.id.rb_put_general);
        }
        else if(type.equalsIgnoreCase("Paper")){
            typeRadioGroup.check(R.id.rb_put_paper);
        }
        else if(type.equalsIgnoreCase("Bread")){
            typeRadioGroup.check(R.id.rb_put_bread);
        }
        else if(type.equalsIgnoreCase("Cloth")){
            typeRadioGroup.check(R.id.rb_put_cloth);
        }
        else if(type.equalsIgnoreCase("Other")){
            typeRadioGroup.check(R.id.rb_put_other);
        }
        //status
        if(status.equalsIgnoreCase("Empty")){
            statusRadioGroup.check(R.id.rb_put_empty);
        }
        else if(status.equalsIgnoreCase("Normal")){
            statusRadioGroup.check(R.id.rb_put_normal);
        }
        else if(status.equalsIgnoreCase("Full")){
            statusRadioGroup.check(R.id.rb_put_full);
        }


        //add listener to button to call PutJsonTask
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedTypeRadioButton = findViewById(typeRadioGroup.getCheckedRadioButtonId());
                RadioButton checkedStatusRadioButton = findViewById(statusRadioGroup.getCheckedRadioButtonId());
                PutJsonTask putJsonTask = new PutJsonTask(Constants.url,PutActivity.this);
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Id",id);
                    jsonObject.put("TypeId",checkedTypeRadioButton.getText().toString());
                    jsonObject.put("StatusId",checkedStatusRadioButton.getText().toString());
                    putJsonTask.execute(jsonObject);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
