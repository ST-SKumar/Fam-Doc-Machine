package com.example.famdocmachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static android.view.View.GONE;

public class Patient extends Activity {

    private static final int CODE_GET = 1024;
    private static final int CODE_POST = 1025;
    ProgressBar progressBar;
    static List<Pt> ptList;

    EditText edtPhone,edtName;
    Spinner edtAge;
    static int pId=0;
    static String name,phone,age,otpRes;
    int randomNumber,gok;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        progressBar = findViewById(R.id.progressBar);
        ptList = new ArrayList<>();
        edtPhone = findViewById(R.id.edtPhone);
        edtName = findViewById(R.id.edtName);
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //-----------------------------------//

        edtAge = findViewById(R.id.spinner);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.age));
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtAge.setAdapter(ad);
    }
    public void confirm2(View view)
    {
        inPt(edtName.getText().toString(),edtPhone.getText().toString(),edtAge.getSelectedItem().toString());
        startActivity(new Intent(this, Diseases.class));
//        startActivity(new Intent(this, Medicine.class));
    }
    public void confirm(View view)
    {
        cg();
    }
    public void cg(){
            int nameOK=0,numOK=0;
            String phoneNumber = edtPhone.getText().toString().trim();
            String pName = edtName.getText().toString().trim();
            if (pName.length() > 0){nameOK = 1;}else {edtName.setError("Name is Required");edtName.requestFocus();}
            if(phoneNumber.length() > 0){if (phoneNumber.length() > 9&&phoneNumber.length() < 13) {if (Character.getNumericValue(phoneNumber.charAt(0))>5 && Character.getNumericValue(phoneNumber.charAt(0))<=9) {numOK = 1;} else {edtPhone.setError("Invalid Phone Number1");edtPhone.requestFocus();}} else{edtPhone.setError("Invalid Phone Number2");edtPhone.requestFocus();}}
            else {edtPhone.setError("Phone Number is required");edtPhone.requestFocus();}
            if (nameOK==1&&numOK==1){ch(edtName.getText().toString(),edtPhone.getText().toString(),edtAge.getSelectedItem().toString());}
    }
    public void otpSend(){
        try {
            // Construct data
//            String apiKey = "apikey=" + "wUOplnFlyyc-rEAvRHKhOcFSMmDXpcxatTLnfyWuxE";
            String apiKey = "apikey=" + "Ek6sgY0CBTg-f67dbnK1IZFyWho2GHAu6WMViPAqld";
            Random random=new Random();
            randomNumber=random.nextInt(9999);
            String message = "&message=" + "Fam Doc Machine\nHey "+edtName.getText().toString()+" Your OTP is "+randomNumber;
//            String sender = "&sender=" + "TXTLCL";
            String sender = "&sender=" + "TSN";
            String numbers = "&numbers=" + edtPhone.getText().toString();

            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
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

            Toast.makeText(getApplicationContext(), "OTP("+randomNumber+") Send SUCCESSFULLY", Toast.LENGTH_LONG).show();
            ott();
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "ERROR SMS "+e, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please check your Data Connection.", Toast.LENGTH_LONG).show();
        }
    }
    public void otpSend2(){
        try {
            // Construct data
//            String apiKey = "apikey=" + "wUOplnFlyyc-rEAvRHKhOcFSMmDXpcxatTLnfyWuxE";
            String apiKey = "apikey=" + "RkhHeWONQNk-xEgHN01960MHAChyR1QaW1UtKd5qpm";
            Random random=new Random();
            randomNumber=random.nextInt(9999);
            String message = "&message=" + "Fam Doc Machine\nHey "+edtName.getText().toString()+" Your OTP is "+randomNumber;
//            String sender = "&sender=" + "TXTLCL";
            String sender = "&sender=" + "FDM";
            String numbers = "&numbers=" + edtPhone.getText().toString();

            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
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
            otpRes=stringBuffer.toString();
            Toast.makeText(getApplicationContext(), "OTP Send To "+edtPhone.getText().toString()+" SUCCESSFULLY", Toast.LENGTH_LONG).show();
            ott();
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "ERROR SMS "+e, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please check your Data Connection.", Toast.LENGTH_LONG).show();
        }
    }
    public void okGo(){
        if (gok!=1)inPt(edtName.getText().toString(),edtPhone.getText().toString(),edtAge.getSelectedItem().toString());
        gok=0;

        startActivity(new Intent(this, Diseases.class));
    }
    void ott(){
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
//        dialog.setTitle("Title...");
        // set the custom dialog components - text, image and button
        final String info=getResources().getString(R.string.otpInfo)+" "+edtPhone.getText().toString();
        TextView otpInfo=dialog.findViewById(R.id.otp_info);
        otpInfo.setText(info);
        //--------------------------------------
        final EditText[] otpView=new EditText[4];
        otpView[0]=dialog.findViewById(R.id.otp1);
        otpView[1]=dialog.findViewById(R.id.otp2);
        otpView[2]=dialog.findViewById(R.id.otp3);
        otpView[3]=dialog.findViewById(R.id.otp4);
        Button verified=dialog.findViewById(R.id.verified);
        //--------------------------------------
        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder otpCode= new StringBuilder();
                for (EditText editText : otpView) {
                    otpCode.append(rS(editText));
                }
                Toast.makeText(context, randomNumber+"=="+otpCode, Toast.LENGTH_SHORT).show();
                if (otpCode.length()>0){
                    if (randomNumber==Integer.valueOf(otpCode.toString())){
                        gok=0;
                        okGo();
                        dialog.dismiss();
                    }else {otpView[3].setError("Wrong OTP!");}
                }else {otpView[3].setError("Please fill OTP.");}
            }
            String rS(EditText editText) {
                return editText.getText().toString().trim();
            }
        });
        for (int i=0;i<4;i++){
            otpView[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                boolean isMaxLength(int index){return otpView[index].length()==1;}
                int cp=0;
                @Override
                public void afterTextChanged(Editable s) {
                    int cnt=0;
                    if (isMaxLength(cnt)){
                        cnt=1;
                        if (isMaxLength(cnt)){
                            cnt=2;
                            if (isMaxLength(cnt)){
                                cnt=3;
                                if (isMaxLength(cnt)){
                                    otpView[0].clearFocus();
                                }
                            }
                        }
                    }
                    if (cp<cnt)otpView[cnt].requestFocus();
                    if (otpView[0].length()>0){
                        cnt=0;
                        if (otpView[1].length()>0){
                            cnt=1;
                            if (otpView[2].length()>0){
                                cnt=2;
                                if (otpView[3].length()>0){
                                    cnt=3;
                                }
                            }
                        }
                    }
                    if (cp>cnt)otpView[cnt].requestFocus();
                    cp=cnt;
                }
            });
        }

        dialog.show();

    }
    /*public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }*/





    private void refreshPt(JSONArray heroes) throws JSONException {
        ptList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);
            pId=obj.getInt("patient_id");
            name=obj.getString("name");
            phone=obj.getString("phone");
            age=obj.getString("age");
            ptList.add(new Pt(
                    obj.getInt("patient_id"),
                    obj.getString("name"),
                    obj.getString("phone"),
                    obj.getString("age")
            ));
        }
//        Toast.makeText(this, ""+ptList.size(), Toast.LENGTH_SHORT).show();
        if (ptList.size()!=0){gok=1;okGo();}else {otpSend();}
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
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    refreshPt(object.getJSONArray("pts"));
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

    private void inPt(String name,String phone,String age) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("age", age);

        Patient.PerformNetworkRequest request = new Patient.PerformNetworkRequest(Api.URL_inPt, params, CODE_POST);
        request.execute();
    }
    private void ch(String name,String phone,String age) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("age", age);

        Patient.PerformNetworkRequest request = new Patient.PerformNetworkRequest(Api.URL_ch, params, CODE_POST);
        request.execute();
    }

    public String check(String name,String phone,String age) {
        try {
            // Construct data
            String param = "&name=" + name + "&phone="+phone+"&age="+age;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL(Api.URL_ch).openConnection();
            String data =param;
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
