package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.kindee.Model.ResultLogin;
import com.example.apple.kindee.Model.Type;
import com.example.apple.kindee.Model.Types;
import com.example.apple.kindee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Apple on 11/19/2017 AD.
 */

public class AddresturantActivity extends Activity{
    Bitmap bitmap;
    int requestCodeMap=2;
    Spinner spinner2;
    boolean check = true;
    List<Type> list;
    Button SelectImageGallery, UploadImageServer;

    ImageView imageView;

    EditText imageName;

    ProgressDialog progressDialog ;

    String GetImageNameEditText;

    String ImageName = "image_name" ;

    String ImagePath = "image_path" ;
    String latitude = "latitude";
    String ServerUploadPath ="http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Resturant_controller/addResturant" ;

    Double lat=0.0,lng=0.0;
    String Res_name;
    String Res_comment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresturant);

        getJsonFromUrlType("http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Resturant_controller/getTypes");

        imageView = (ImageView)findViewById(R.id.imageView);

        imageName = (EditText)findViewById(R.id.editTextImageName);

        SelectImageGallery = (Button)findViewById(R.id.buttonSelect);

        UploadImageServer = (Button)findViewById(R.id.buttonUpload);

        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameEditText = imageName.getText().toString();
                EditText edt_Res_name = (EditText) findViewById(R.id.edt_Resname_Addresturant);
                EditText edt_Res_comment = (EditText) findViewById(R.id.edt_comment_addresturant);

                Res_name = edt_Res_name.getText().toString();
                Res_comment = edt_Res_comment.getText().toString();
                ImageUploadToServerFunction();

            }
        });
    }

    public void callMapUpload(View view){
        Intent i = new Intent(this,MapsUploadActivity.class);
        startActivityForResult(i,requestCodeMap);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == requestCodeMap){
            TextView tv = (TextView) findViewById(R.id.test);
                tv.setText(data.getExtras().getDouble("latitude")+"");
            lat = data.getExtras().getDouble("latitude");
            lng = data.getExtras().getDouble("longitude");
            Toast.makeText(this,lat+"",Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    private void getJsonFromUrl(String url){
        //final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setPrettyPrinting();

                        Gson gson = gsonBuilder.create();
                        ResultLogin result = gson.fromJson(response.toString(),ResultLogin.class);

                        //ArrayAdapter<String> arrayAdapter = ArrayAdapter
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }
    private void getJsonFromUrlType(String url){
        //final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setPrettyPrinting();

                        Gson gson = gsonBuilder.create();
                        Type []types = gson.fromJson(response.toString(),Type[].class);
                        Toast.makeText(getBaseContext(),types[0].Type_id+"",Toast.LENGTH_SHORT).show();
                        list = Arrays.asList(types);

                        spinner2 = (Spinner) findViewById(R.id.spinner);

                        ArrayAdapter<Type> dataAdapter = new ArrayAdapter<Type>(getBaseContext(),
                                android.R.layout.simple_spinner_item, list);

                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter);

                        //ArrayAdapter<String> arrayAdapter = ArrayAdapter
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }



    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(AddresturantActivity.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(AddresturantActivity.this,string1, Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put("image_name", GetImageNameEditText);

                HashMapParams.put("image_path", ConvertImage);
                int indexselect=1;
                for(int i=0;i<list.size();i++){
                    if(spinner2.getSelectedItem().toString()==list.get(i).Type_name){
                        indexselect = list.get(i).Type_id;
                        break;
                    }
                }
                HashMapParams.put("Res_Type_id",""+indexselect);
                HashMapParams.put("Res_latitude",lat+"");
                HashMapParams.put("Res_longitude",lng+"");
                HashMapParams.put("Res_name",Res_name);
                HashMapParams.put("Res_detail",Res_comment);

                //HashMapParams.put("longitude",lng+"");
                //HashMapParams.put("Res_name",Res_name);
                //HashMapParams.put("Res_comment",Res_comment);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }
    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

}
