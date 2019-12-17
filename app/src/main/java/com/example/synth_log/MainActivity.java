package com.example.synth_log;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editPatch;
    Spinner editDevice;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnSearch;
    Button btnClear;
    Button btnUpdateDevices;
    ArrayList<String> deviceList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editDevice = (Spinner) findViewById(R.id.spinner);
        editPatch = (EditText) findViewById(R.id.editText_patch);
        btnAddData = (Button) findViewById(R.id.button_addDevice);
        btnViewAll = (Button) findViewById(R.id.button_deleteDevice);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnSearch = (Button) findViewById(R.id.button_search);
        btnClear = (Button) findViewById(R.id.button_clear);
        btnUpdateDevices = (Button) findViewById(R.id.button_updateDevices);


        AddData();
        ViewAll();
        DeleteData();
        SearchData();
        ClearData();
        UpdateDevices();

        deviceList = new ArrayList<>();
        Cursor res = myDb.getAllDevices();
        while(res.moveToNext()){
            deviceList.add(res.getString(0));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDevice.setAdapter(arrayAdapter);

        if (getIntent().hasExtra("METHOD TYPE")) {
    //            Toast.makeText(MainActivity.this,getIntent().getExtras().getInt("METHOD TYPE") +
    //                    " " + getIntent().getExtras().getString("DEVICE NAME"), Toast.LENGTH_LONG).show();
                switch (getIntent().getExtras().getInt("METHOD TYPE")) {
                    case 1:
                        //ADD button clicked
                        deviceList.add(getIntent().getExtras().getString("DEVICE NAME"));
                        break;

                    case 2:
                        //DELETE button clicked
                        deviceList.remove(getIntent().getExtras().getString("DEVICE NAME"));
                        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        myDb.deleteDevice(getIntent().getExtras().getString("DEVICE NAME"));
                        break;
                }
            }
        else if(myDb.getAllData().getCount()==0){
//            Toast.makeText(MainActivity.this,"", Toast.LENGTH_LONG).show();
            showMessage("DATABASE EMPTY", "Please add a new device");
            Intent startIntent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(startIntent);
        }
    }





    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editDevice.getSelectedItem().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());
                if (isInserted == true){
                    Toast.makeText(MainActivity.this,"Patch inserted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Patch not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void ViewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Cursor res = myDb.getAllData();
                if(res.getCount()==0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Device: " + res.getString(0)+"\n");
                    buffer.append("Patch: " + res.getString(1)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void SearchData(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.searchData(editDevice.getSelectedItem().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());
                if (res.getCount()!=0){
                    Toast.makeText(MainActivity.this,"Patch exists", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Patch does not exist", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editDevice.getSelectedItem().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());

                if(deletedRows>0){
                    Toast.makeText(MainActivity.this,"Patch deleted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Patch not deleted", Toast.LENGTH_LONG).show();
                }
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


    public void ClearData(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearDataHelper();
            }
        });
    }



    public void ClearDataHelper(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Integer deletedRows = myDb.clearData();
                        if(deletedRows>0){
                            Toast.makeText(MainActivity.this,"Table deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Table not deleted", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(MainActivity.this,"Table not deleted", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void UpdateDevices(){
        btnUpdateDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(startIntent);
            }
        });
    }




}
