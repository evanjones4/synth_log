package com.example.synth_log;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editDevice, editPatch;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnSearch;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editDevice = (EditText) findViewById(R.id.editText_name);
        editPatch = (EditText) findViewById(R.id.editText_surname);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewall);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnSearch = (Button) findViewById(R.id.button_search);
        btnClear = (Button) findViewById(R.id.button_clear);
        AddData();
        ViewAll();
        DeleteData();
        SearchData();
        ClearData();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editDevice.getText().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());
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
                Cursor res = myDb.searchData(editDevice.getText().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());
                if (res.getCount()!=0){
                    Toast.makeText(MainActivity.this,"Patch is being used. Do no overwrite", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Patch is not being used. OK to overwrite", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editDevice.getText().toString().toUpperCase(),editPatch.getText().toString().toUpperCase());
                if(deletedRows>0){
                    Toast.makeText(MainActivity.this,"Patch deleted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Patch not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void ClearData(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDataHelper();
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


    public void clearDataHelper(){
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



//    public void UpdateData(){
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isUpdate = myDb.updateData(editId.getText().toString(),editName.getText().toString(),
//                        editSurname.getText().toString(),editMarks.getText().toString());
//                if (isUpdate==true){
//                    Toast.makeText(MainActivity.this,"Data updated", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Data not updated", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }



}
