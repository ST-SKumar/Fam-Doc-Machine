package com.example.famdocmachine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button doctor = findViewById(R.id.doctor);
        Button patient = findViewById(R.id.patient);

        doctor.setOnClickListener(this);
        patient.setOnClickListener(this);
    }


    //Codes for Exit Dialog Box Starts
    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage("Are You Sure You Want to Exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    //Codes for Exit Dialog Box Ends

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate my_options_menu.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.help:
                Toast.makeText(this,"Help me!\n:)", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.about:
                startActivity(new Intent(this, ChangeIP.class));
//                startActivity(new Intent(this, About.class));
//                Toast.makeText(this,"I am Sandeep Kumar .\nThis is My First App!!!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.doctor:
                startActivity(new Intent(this, Doctor.class));
                break;
            case R.id.patient:
                startActivity(new Intent(this, Patient.class));
                break;
        }
    }

    public void cngIp(View view) {
//        startActivity(new Intent(this, ChangeIP.class));
        startActivity(new Intent(this, ShowMe.class));
    }
}
