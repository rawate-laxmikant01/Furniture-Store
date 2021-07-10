package com.example.navigationbar.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationbar.AddDeliveryAddress;
import com.example.navigationbar.MainActivity;
import com.example.navigationbar.R;
import com.example.navigationbar.UserLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountFragment extends Fragment {

    Button myadress,logout;
    FirebaseAuth auth;

    EditText fname,lname;
    TextView submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    private MyAccountViewModel myAccountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myAccountViewModel =
                ViewModelProviders.of(this).get(MyAccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);

        fname=root.findViewById(R.id.textView10);
        lname=root.findViewById(R.id.textView11);

        submit=root.findViewById(R.id.textView13);
        logout=root.findViewById(R.id.btn_logout);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid()).child("information");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Fname").setValue(fname.getText().toString());
                databaseReference.child("Lname").setValue(lname.getText().toString());
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                    String name=snapshot.child("Fname").getValue(String.class);
                    String lastname=snapshot.child("Lname").getValue(String.class);

                    fname.setText(name);
                    lname.setText(lastname);

               // Toast.makeText(getContext(), ""+name+" "+lastname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(getContext(), UserLoginActivity.class);
                startActivity(intent);
            }
        });

        myadress=root.findViewById(R.id.btn_myadress);
         myadress.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getActivity(), AddDeliveryAddress.class);
                 startActivity(intent);
             }
         });

        return root;
    }
}