package com.example.synth_log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    EditText editDevice;
    Button btnAdd;
    Button btnDelete;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editDevice = (EditText) findViewById(R.id.edit_device);
        btnAdd = (Button) findViewById(R.id.button_addDevice);
        btnDelete = (Button) findViewById(R.id.button_deleteDevice);
        back = (Button) findViewById(R.id.button_back);

        AddDevice();
        DeleteDevice();
        Back();
    }

    public void AddDevice(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.putExtra("METHOD TYPE", 1);
                    intent.putExtra("DEVICE NAME", editDevice.getText().toString().toUpperCase());
                    startActivity(intent);
                }
        });
    }

//    public void DeleteDevice(){
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                intent.putExtra("METHOD TYPE", 2);
//                intent.putExtra("DEVICE NAME", editDevice.getText().toString().toUpperCase());
//                startActivity(intent);
//            }
//        });
//    }

    public void DeleteDevice(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                intent.putExtra("METHOD TYPE", 2);
                                intent.putExtra("DEVICE NAME", editDevice.getText().toString().toUpperCase());
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setMessage("Are you sure you want to delete " +  editDevice.getText().toString().toUpperCase() + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    public void Back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
