package com.example.navigationbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.Adapter.FourItemViewAdapter;
import com.example.navigationbar.Adapter.ItemGridViewAdapter;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FourItemViewAdapter fourItemViewAdapter;
    private ArrayList<ItemGridViewModel> list;
    FirebaseFirestore firestore ;
    CollectionReference collectionReference;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firestore = FirebaseFirestore.getInstance();

        collectionReference=firestore.collection("Catagoires");
        firestore.collection("Catagoires")
                .document("Beds").collection("local");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyCartActivity.class));
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
//                ,R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.home_gridview_recyclerview);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return false;
            }
        });
        return true;
    }

    private void searchData(String search) {

        collectionReference.document("Beds").collection("local")
               // .orderBy("price", (Query.Direction.ASCENDING))
                  .whereEqualTo("name",search)
                // .orderBy("price").startAt(Integer.parseInt("0"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear();
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                Toast.makeText(MainActivity.this, "searrched : " , Toast.LENGTH_SHORT).show();
                                list.add(new ItemGridViewModel(d.get("name").toString(), d.get("itemimg").toString(), d.getString("price")
                                        , d.getString("mrpprice"), d.getString("discount")
                                        , d.getString("totalquantity"), d.getString("id"), d.getString("category"),
                                        d.getString("brand"), d.getString("color")));

                            }
                            ItemGridViewAdapter itemGridViewAdapter = new ItemGridViewAdapter(list,MainActivity.this);
                            recyclerView.setAdapter(itemGridViewAdapter);
                        }
                    }
                });

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notificatio:
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cartitem:
                startActivity(new Intent(MainActivity.this, MyCartActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}