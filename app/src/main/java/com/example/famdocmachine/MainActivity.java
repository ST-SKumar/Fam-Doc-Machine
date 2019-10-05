package com.example.famdocmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button doctor,patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctor = findViewById(R.id.doctor);
        doctor();

        patient = findViewById(R.id.patient);
        patient();
    }
    public void doctor()
    {
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Doctor.class);
                startActivity(intent);
//        Toast.makeText(this, "It Doctor !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void patient()
    {

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Patient.class);
                startActivity(intent);
//        Toast.makeText(this, "It Patient !", Toast.LENGTH_LONG).show();
            }
        });
    }
}
