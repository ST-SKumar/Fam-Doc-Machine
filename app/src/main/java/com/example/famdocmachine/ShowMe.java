package com.example.famdocmachine;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowMe extends Activity {
    EditText edtN, edtP, vP;
    static List<Pt> rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_me);

        edtN = findViewById(R.id.edtN);
        edtP = findViewById(R.id.edtP);
        vP = findViewById(R.id.vP);
        rec = new ArrayList<>();

        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void confirm(View view) {
        boolean res = getRec(edtN.getText().toString(),edtP.getText().toString());
        if (res){
            Toast.makeText(this, "Length "+rec.size(), Toast.LENGTH_LONG).show();
        }
    }
    private void detIns(JSONArray heroes) throws JSONException {
        rec.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);
            rec.add(new Pt(
                    obj.getInt("pid"),
                    obj.getString("name"),
                    obj.getString("phone"),
                    obj.getString("age"),
                    obj.getString("date"),
                    obj.getString("dise"),
                    obj.getString("med"),
                    obj.getString("qtt"),
                    obj.getInt("price"),
                    obj.getInt("paid")
            ));
        }
    }

    public boolean getRec(String name,String phone) {
        try {
            // Construct data
            String data = "&name=" + name + "&phone="+phone;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL(Api.URL_getRec2).openConnection();
//            String data =param;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            JSONObject det = new JSONObject(stringBuffer.toString());
            if (!det.getBoolean("error")) {
                Toast.makeText(getApplicationContext(), det.getString("message"), Toast.LENGTH_SHORT).show();

                detIns(det.getJSONArray("Rec"));
            }
            vP.setText(stringBuffer.toString());
//            return stringBuffer.toString();
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Please check your Data Connection.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public String getRec(String pid) {
        try {
            // Construct data
            String data = "&pid=" + pid;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL(Api.URL_getRec).openConnection();
//            String data =param;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            JSONObject object = new JSONObject(stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}
