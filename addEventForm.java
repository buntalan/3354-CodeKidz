package com.example.addeventbutton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.lang.UCharacterEnums;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Build.*;

public class addEventForm extends AppCompatActivity {
    EditText date,name,time;
    Button saveButton;
    @RequiresApi(api = VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_form);
        //saving the events
        if((VERSION.SDK_INT >= VERSION_CODES.M) && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        name= (EditText) findViewById(R.id.name);
        date=(EditText) findViewById(R.id.date);
        time=(EditText) findViewById(R.id.time);
        saveButton=(Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //savinf Data with Button
            public void onClick(View v) {
                //make the linkedList to string here
            String filename= name.getText().toString();
           // Date date= date.getDate().toString();
            //Time  time = time.getTime().toString();
                if (filename!="") {
                    //saveTextasFile(filename, date,time);
                }

            }
        });

    }
    private void saveTextasFile(String filename,String content){
        String fileName = filename+".txt";
        //creating File
        File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        //Writing to a file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "Saved!",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File Not Found",Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error Saving",Toast.LENGTH_SHORT).show();
        }
    }
    private void requestPermissions(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"successfully Saved",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Error!",Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }


    }
}
