package com.example.navigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationbar.Model.AdressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDeliveryAddress extends AppCompatActivity {
    private EditText pinCodeEdt, adstate, fullname, phoneno, houseno, roadno;
    TextView homecolor, workcolor;
    String pinCode,type;
    private RequestQueue mRequestQueue;   // creating a variable for request queue.
    Spinner village;
    ArrayList<String> list;

    Button saveadress;
    String Villagename, district, state, country;
    ArrayAdapter<String> villageadapter;
    String villagestore;

    CheckBox home, work;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //String fullname,phoneno,pincode,state,city,houseno,roadname,typeAd;
    Boolean fullnameverify,phoneverify,pincodeverify,stateverify,cityverify,houseverify,roadnameverify,typeverify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);

        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("userdata")
                .child(auth.getCurrentUser().getUid()).child("Address");

        fullname = findViewById(R.id.fullname_id);
        phoneno = findViewById(R.id.phoneNoid);
        houseno = findViewById(R.id.housenoid);
        roadno = findViewById(R.id.Roadnameid);

        homecolor = findViewById(R.id.adhomecolor);
        workcolor = findViewById(R.id.adworkcolor);
        home = findViewById(R.id.adhome);
        work = findViewById(R.id.adwork);
        adstate = findViewById(R.id.stateid);
        village = findViewById(R.id.village);
        saveadress=findViewById(R.id.btn_saveAdress);
        list = new ArrayList<>();
        pinCodeEdt = findViewById(R.id.idedtPinCode);
        mRequestQueue = Volley.newRequestQueue(AddDeliveryAddress.this);        // initializing our request que variable with request
        // queue and passing our context to it.

        home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    work.setChecked(false);
                    homecolor.setVisibility(View.VISIBLE);
                    home.setVisibility(View.INVISIBLE);

                    workcolor.setVisibility(View.INVISIBLE);
                    work.setVisibility(View.VISIBLE);

                    type="home";
                }
            }
        });
        work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    home.setChecked(false);

                    homecolor.setVisibility(View.INVISIBLE);
                    home.setVisibility(View.VISIBLE);

                    workcolor.setVisibility(View.VISIBLE);
                    work.setVisibility(View.INVISIBLE);

                    type="work";
                }
            }
        });
        pinCodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pinCode = s.toString();
                getDataFromPinCode(pinCode);
                pincodevillage();


            }
        });

        saveadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String fullname,phoneno,pincode,state,city,houseno,roadname,typeAd;

                String name=fullname.getText().toString().trim();
                String phone=phoneno.getText().toString().trim();
                String house=houseno.getText().toString().trim();
                String road=roadno.getText().toString().trim();

                AdressModel adressModel=new AdressModel(name,phone,pinCode,state,villagestore,house,road,type);
                if (name.isEmpty()) {
                    fullname.setError(getResources().getString(R.string.name_error));
                    fullnameverify = false;
                } else {
                    fullnameverify = true;
                }
                if (phoneno.getText().toString().isEmpty()) {
                    phoneno.setError(getResources().getString(R.string.phone_error));
                    phoneverify = false;
                } else {
                    phoneverify = true;
                }
                //pinCodeEdt, adstate, fullname, phoneno, houseno, roadno;
                if (pinCodeEdt.getText().toString().isEmpty()) {
                    pinCodeEdt.setError(getResources().getString(R.string.phone_error));
                    pincodeverify = false;
                } else {
                    pincodeverify = true;
                }
                if (adstate.getText().toString().isEmpty()) {
                    adstate.setError(getResources().getString(R.string.phone_error));
                    stateverify = false;
                } else {
                    stateverify = true;
                }
                if (village.toString().isEmpty()) {
//                    village.setError(getResources().getString(R.string.phone_error));
                    Toast.makeText(AddDeliveryAddress.this, "Please select your village", Toast.LENGTH_SHORT).show();
                    cityverify = false;
                } else {
                    cityverify = true;
                }
                if (houseno.toString().isEmpty()) {
                    houseno.setError(getResources().getString(R.string.phone_error));
                    houseverify = false;
                } else {
                    houseverify = true;
                }
                if (roadno.toString().isEmpty()) {
                    roadno.setError(getResources().getString(R.string.phone_error));
                    roadnameverify = false;
                } else {
                    roadnameverify = true;
                }
                if (type.isEmpty()) {
                   // type.setError(getResources().getString(R.string.phone_error));
                    Toast.makeText(AddDeliveryAddress.this, "Please select type", Toast.LENGTH_SHORT).show();
                    typeverify = false;
                } else {
                    typeverify = true;
                }

                if(fullnameverify&&phoneverify&&pincodeverify&&stateverify&&cityverify&&
                        houseverify&&roadnameverify&&typeverify){

                    databaseReference.setValue(adressModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddDeliveryAddress.this, "Address added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDeliveryAddress.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }


    private void getDataFromPinCode(String pinCode) {

        // clearing our cache of request queue.
        mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(AddDeliveryAddress.this);

        // in below line we are creating a
        // object request using volley.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside this method we will get two methods
                // such as on response method
                // inside on response method we are extracting
                // data from the json format.
                try {
                    // we are getting data of post office
                    // in the form of JSON file.

                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");

                    if (response.getString("Status").equals("Error")) {
                        // validating if the response status is success or failure.
                        // in this method the response status is having error and
                        // we are setting text to TextView as invalid pincode.
                        //  pinCodeDetailsTV.setText("Pin code is not valid.");
                        Toast.makeText(AddDeliveryAddress.this, "Pin code is not valid.", Toast.LENGTH_SHORT).show();
                    } else {
                        // if the status is success we are calling this method
                        // in which we are getting data from post office object
                        // here we are calling first object of our json array.

                        for (int i = 0; i < postOfficeArray.length(); i++) {
                            JSONObject obj = postOfficeArray.getJSONObject(i);

                            // inside our json array we are getting district name,
                            // state and country from our data.
                            Villagename = obj.getString("Name");
                            district = obj.getString("District");
                            state = obj.getString("State");
                            country = obj.getString("Country");

                            adstate.setText(state);

                            list.add(Villagename);

                        }


                    }
                } catch (JSONException e) {
                    // if we gets any error then it
                    // will be printed in log cat.
                    e.printStackTrace();
                    //     pinCodeDetailsTV.setText("Pin code is not valid");
                    Toast.makeText(AddDeliveryAddress.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // below method is called if we get
                // any error while fetching data from API.
                // below line is use to display an error message.
                //     Toast.makeText(AddDeliveryAddress.this, "Pin code is not valid.", Toast.LENGTH_SHORT).show();
                //   pinCodeDetailsTV.setText("Pin code is not valid");
                Toast.makeText(AddDeliveryAddress.this, "Pin code is not valid." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest);


    }

    private void pincodevillage() {

        villageadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        village.setAdapter(villageadapter);

        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                villagestore = list.get(position);

                //temperory value have been stored in ethinicity_store of list which user have been selected.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddDeliveryAddress.this, "ethernity not selecteed", Toast.LENGTH_SHORT).show();
            }
        });

    }


}