package com.example.famdocmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button doc,patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doc = findViewById(R.id.doctor);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctor();
            }
        });
        patient = findViewById(R.id.patient);
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient();
            }
        });
    }
    public void doctor()
    {
        Intent intent = new Intent(MainActivity.this, Doctor.class);
        startActivity(intent);
        Toast.makeText(this, "It Doctor !", Toast.LENGTH_LONG).show();
    }
    public void patient()
    {
        Intent intent = new Intent(MainActivity.this,Patient.class);
        startActivity(intent);
        Toast.makeText(this, "It Patient !", Toast.LENGTH_LONG).show();
    }
}
