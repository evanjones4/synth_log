package com.example.synth_log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    EditText editDevice;
    ArrayList<String> deviceList = new ArrayList<String>();
    Button btnAdd;
    Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editDevice = (EditText) findViewById(R.id.edit_device);
        btnAdd = (Button) findViewById(R.id.button_addDevice);
        btnDelete = (Button) findViewById(R.id.button_deleteDevice);

        AddDevice();
        DeleteDevice();
    }

    public void AddDevice(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.putExtra("METHOD TYPE", 1);
                    intent.putExtra("DEVICE NAME", editDevice.getText().toString().toUpperCase());
                    startActivity(intent);
//                Toast.makeText(Main2Activity.this,"Added", Toast.LENGTH_LONG).show();
                }
        });
    }

    public void DeleteDevice(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("METHOD TYPE", 2);
                intent.putExtra("DEVICE NAME", editDevice.getText().toString().toUpperCase());
                startActivity(intent);
//              Toast.makeText(Main2Activity.this,"Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
