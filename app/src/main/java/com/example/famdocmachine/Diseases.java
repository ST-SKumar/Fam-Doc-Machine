package com.example.famdocmachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Diseases extends Activity implements View.OnClickListener{

    CheckBox cbFever,cbCold,cbCough,cbKPain,cbNPain;
    RangeBar rbFever,rbCold,rbCough,rbKPain,rbNPain;
    private static final int CODE_GET = 1024;
    private static final int CODE_POST = 1025;
    static List<Pt> mdList,dtList;
    static String rec="",med,qt;
    ProgressBar progressBar;
    static int mid,did,pid,prc;
    String[] dise = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);
        mdList = new ArrayList<>();
        dtList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);

        // CheckBox Casting
        cbFever = findViewById(R.id.cbFever);
        cbCold = findViewById(R.id.cbCold);
        cbCough = findViewById(R.id.cbCough);
        cbKPain = findViewById(R.id.cbKPain);
        cbNPain = findViewById(R.id.cbNPain);
        // RangeBar Casting
        rbFever = findViewById(R.id.rbFever);
        rbCold = findViewById(R.id.rbCold);
        rbCough = findViewById(R.id.rbCough);
        rbKPain = findViewById(R.id.rbKPain);
        rbNPain = findViewById(R.id.rbNPain);

        cbFever.setOnClickListener(this);
        cbCold.setOnClickListener(this);
        cbCough.setOnClickListener(this);
        cbKPain.setOnClickListener(this);
        cbNPain.setOnClickListener(this);
        //Set SeekBar Disable
        setEnD(rbFever);
        setEnD(rbCold);
        setEnD(rbCough);
        setEnD(rbKPain);
        setEnD(rbNPain);
        //Set Tic Position
        rbFever.setSeekPinByValue(rbFever.getTickStart());
        rbCold.setSeekPinByValue(rbCold.getTickStart());
        rbCough.setSeekPinByValue(rbCough.getTickStart());
        rbKPain.setSeekPinByValue(rbKPain.getTickStart());
        rbNPain.setSeekPinByValue(rbNPain.getTickStart());
    }
    void setEnD(View v){v.setEnabled(!v.isEnabled());}
    void cb(CheckBox c,int i){if(c.isChecked()) dise[i]=c.getText().toString(); else dise[i]="";}
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cbFever:
                setEnD(rbFever);
                cb(cbFever,0);
                break;
            case R.id.cbCold:
                setEnD(rbCold);
                cb(cbCold,1);
                break;
            case R.id.cbCough:
                setEnD(rbCough);
                cb(cbCough,2);
                break;
            case R.id.cbKPain:
                setEnD(rbKPain);
                cb(cbKPain,3);
                break;
            case R.id.cbNPain:
                setEnD(rbNPain);
                cb(cbNPain,4);
                break;
        }
    }

    public void confirm(View v){
        List<String> list = new ArrayList<>();
        for(String s : dise) {
            if(s != null && s.length() > 0) {
                list.add(s);
            }
        }
        String[] dice = list.toArray(new String[0]);
        String a = TextUtils.join(",", dice);
//        Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();
        rec="Med";
        if(!a.equals("")){getMd(Patient.age,a);}
    }

    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a2 GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a2 progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (rec.equals("Med")){refreshMd(object.getJSONArray("Med"));}
                    else if (rec.equals("Dt")){refreshDt(object.getJSONArray("Dt"));}
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void refreshMd(JSONArray heroes) throws JSONException {
        mdList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);
            mid=obj.getInt("id");
            med=obj.getString("medicine");
            qt=obj.getString("quantity");
            prc=obj.getInt("price");

            mdList.add(new Pt(
                    obj.getInt("id"),
                    obj.getString("age"),
                    obj.getString("diseases"),
                    obj.getString("medicine"),
                    obj.getString("quantity"),
                    obj.getInt("price")
            ));
        }
        Date date = Calendar.getInstance().getTime();
        rec="Dt";
        inDt(Patient.pId,date.toString(),mid);
    }
    private void refreshDt(JSONArray heroes) throws JSONException {
        dtList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);
            pid=obj.getInt("patient_id");
            did=obj.getInt("id");
            dtList.add(new Pt(
                    obj.getInt("patient_id"),
                    obj.getInt("id"),
                    obj.getString("date"),
                    obj.getInt("med_id"),
                    obj.getInt("paid")
            ));
        }
        progressBar.setVisibility(GONE);
        rec="";
        startActivity(new Intent(this, Medicine.class));
    }

    private void getMd(String age,String dis) {
        HashMap<String, String> params = new HashMap<>();
        params.put("age", age);
        params.put("dis", dis);

        Diseases.PerformNetworkRequest request = new Diseases.PerformNetworkRequest(Api.URL_getMd, params, CODE_POST);
        request.execute();
    }
    private void inDt(int pid, String date, int mid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pid", pid+"");
        params.put("date", date);
        params.put("mid", mid+"");
        params.put("paid", -1 +"");

        Diseases.PerformNetworkRequest request = new Diseases.PerformNetworkRequest(Api.URL_inDt, params, CODE_POST);
        request.execute();
    }

}
