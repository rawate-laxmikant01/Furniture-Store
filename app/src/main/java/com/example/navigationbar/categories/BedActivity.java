package com.example.navigationbar.categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.navigationbar.Adapter.ItemGridViewAdapter;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemGridViewAdapter itemGridViewAdapter;
    private ArrayList<ItemGridViewModel> list;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed);


        firestore= FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Catagoires");

        recyclerView = findViewById(R.id.bed_gridview_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(BedActivity.this,2));
        list = new ArrayList<ItemGridViewModel>();

        String category=getIntent().getStringExtra("category");

        collectionReference.document(category).collection("local")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list.add(new ItemGridViewModel(d.get("name").toString(), d.get("itemimg").toString(),d.getString("price").toString()
                                        ,d.getString("mrpprice").toString(),d.getString("discount").toString()
                                        ,d.getString("totalquantity").toString(),d.getString("id").toString(),d.getString("category").toString(),
                                        d.getString("brand").toString(),d.getString("color").toString()));
                                Toast.makeText(BedActivity.this, "Done..", Toast.LENGTH_SHORT).show();
                                //  String name,itemimg,price,mrpprice,discount,totalquantity,id,category,brand,color;
//
                            }
                            itemGridViewAdapter = new ItemGridViewAdapter(list,BedActivity.this);
                            recyclerView.setAdapter(itemGridViewAdapter);
                            // }
                        } else {
                            String e=task.getException().toString();
                            Toast.makeText(BedActivity.this, "Failed"+e, Toast.LENGTH_SHORT).show();
                        }


                    }
//                recyclerView.setAdapter(itemGridViewAdapter);
                });
    }
}