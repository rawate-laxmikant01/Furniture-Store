package com.example.navigationbar.categories;

import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.Adapter.ItemGridViewAdapter;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemGridViewAdapter itemGridViewAdapter;
    private ArrayList<ItemGridViewModel> list;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    Button sort, filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed);


        sort = findViewById(R.id.btn_sort);
        filter = findViewById(R.id.btn_filter);
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Catagoires");

        recyclerView = findViewById(R.id.bed_gridview_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BedActivity.this));
        list = new ArrayList<ItemGridViewModel>();

        String category = getIntent().getStringExtra("category");

        collectionReference.document(category).collection("local")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                list.add(new ItemGridViewModel(d.get("name").toString(), d.get("itemimg").toString(), d.getString("price")
                                        , d.getString("mrpprice"), d.getString("discount")
                                        , d.getString("totalquantity"), d.getString("id"), d.getString("category"),
                                        d.getString("brand"), d.getString("color")));
                                //  Toast.makeText(BedActivity.this, "Done..", Toast.LENGTH_SHORT).show();
                                //  String name,itemimg,price,mrpprice,discount,totalquantity,id,category,brand,color;
//
                            }
                            itemGridViewAdapter = new ItemGridViewAdapter(list, BedActivity.this);
                            recyclerView.setAdapter(itemGridViewAdapter);
                            // }
                        } else {
                            String e = task.getException().toString();
                            Toast.makeText(BedActivity.this, "Failed" + e, Toast.LENGTH_SHORT).show();
                        }


                    }
//                recyclerView.setAdapter(itemGridViewAdapter);
                });


        PopupMenu sort_popup = new PopupMenu(BedActivity.this, sort);
        sort_popup.getMenuInflater().inflate(R.menu.sort_menu, sort_popup.getMenu());

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        list.clear();
                        collectionReference.document(category).collection("local")
                                //  .orderBy("price",(Query.Direction.ASCENDING))
                                // .collection("local")
                                //      .orderBy("price", Query.Direction.ASCENDING)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                                            ItemGridViewModel itemGridViewModel = d.toObject(ItemGridViewModel.class);
                                            list.add(itemGridViewModel);

                                            Collections.sort(list, new Comparator<ItemGridViewModel>() {
                                                public int compare(ItemGridViewModel lhs, ItemGridViewModel rhs) {
                                                    return lhs.getPrice().compareTo(rhs.getPrice());
                                                }
                                            });
                                        }
                                        itemGridViewAdapter = new ItemGridViewAdapter(list, BedActivity.this);
                                        recyclerView.setAdapter(itemGridViewAdapter);
                                    }
                                });

                        return true;
                    }
                });
                sort_popup.show();
            }
        });
        PopupMenu filter_popup = new PopupMenu(BedActivity.this, filter);
        filter_popup.getMenuInflater().inflate(R.menu.filterpopup, filter_popup.getMenu());

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(BedActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                filter_popup.show();
            }
        });


    }
}