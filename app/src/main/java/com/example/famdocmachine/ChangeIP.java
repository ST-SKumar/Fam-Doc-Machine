package com.example.famdocmachine;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeIP extends Activity {

    EditText ip;
    TextView path;
    public static String ipath = "http://192.168.137.1/fdmApi/v1/Api.php?apicall=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);
        path=findViewById(R.id.path);
        path.setText(ipath);

        ip=findViewById(R.id.eIp);
    }

    public void change(View view) {
        ipath = "http://"+ip.getText()+"/fdmApi/v1/Api.php?apicall=";
        path.setText(ipath);
    }
}
