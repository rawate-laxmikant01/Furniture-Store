package com.example.navigationbar.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.Adapter.CatagoriesAdapter;
import com.example.navigationbar.Adapter.FourItemViewAdapter;
import com.example.navigationbar.Adapter.ItemGridViewAdapter;
import com.example.navigationbar.Adapter.OfferofdayAdapter;
import com.example.navigationbar.Adapter.SliderAdapter;
import com.example.navigationbar.MainActivity;
import com.example.navigationbar.Model.CatagoriesModel;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.R;
import com.example.navigationbar.categories.BedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private SliderView sliderView;
    private RecyclerView catagoriesRecyclerView;
    private RecyclerView recyclerView,offerRecyclerView;
    private FourItemViewAdapter fourItemViewAdapter;
    private ArrayList<ItemGridViewModel> list,offerlist;
    ConstraintLayout constraintLayout2;

    //--------------------------------------------
    ArrayList<CatagoriesModel> catagorieslist;
    CatagoriesAdapter catagoriesAdapter;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    OfferofdayAdapter offerofdayAdapter;
    private ProgressBar homepageprogess;

    Button btn_all_deal, btn_all_super;

    @SuppressLint("CutPasteId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        btn_all_deal = root.findViewById(R.id.btn_viewall_dealofday_id);
        btn_all_super = root.findViewById(R.id.btn_viewall_dealofday_id);
        homepageprogess=root.findViewById(R.id.homepageprogress);

        constraintLayout2=root.findViewById(R.id.constraintLayout2);
       offerRecyclerView = root.findViewById(R.id.home_supersaveroffer_recyclerview);
        sliderView = root.findViewById(R.id.imageSlider);
        catagoriesRecyclerView = root.findViewById(R.id.catagories_recyclerview);
        catagorieslist = new ArrayList<>();
        catagoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Catagoires");
        //-----------------------------------------------
        recyclerView = root.findViewById(R.id.home_gridview_recyclerview);


        btn_all_super.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BedActivity.class);
            intent.putExtra("category", "Beds");
            startActivity(intent);
        });

        catagroryMethod();
        sliderViewCode();
        dealOfTheDay();
        offerproducts();

        return root;
    }

    private void offerproducts() {
        offerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        offerlist = new ArrayList<>();
        collectionReference.document("sofas").collection("local")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot d : task.getResult()) {
                                offerlist.add(new ItemGridViewModel(d.get("name").toString(), d.get("itemimg").toString(),
                                        d.getString("price"), d.getString("mrpprice"), d.getString("discount")
                                        , d.getString("totalquantity"), d.getString("id"), d.getString("category"),
                                        d.getString("brand"), d.getString("color")));
                            }
                            offerofdayAdapter = new OfferofdayAdapter(offerlist, getActivity());
                            offerRecyclerView.setAdapter(offerofdayAdapter);

                        } else {
                            String e = task.getException().toString();
                            Toast.makeText(getContext(), "Failed" + e, Toast.LENGTH_SHORT).show();
                        }


                    }

                });
    }

    private void catagroryMethod() {

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot dataSnapshot : Objects.requireNonNull(task.getResult())) {
                        catagorieslist.add(new CatagoriesModel(Objects.requireNonNull(dataSnapshot.get("img")).toString(), Objects.requireNonNull(dataSnapshot.get("name")).toString()));
                        //  CatagoriesAdapter.notifydatasetchangle();
                    }
                    catagoriesAdapter = new CatagoriesAdapter(catagorieslist, getContext());
                    catagoriesRecyclerView.setAdapter(catagoriesAdapter);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sliderViewCode() {
        int[] images = new int[]{R.drawable.banner1, R.drawable.banner3, R.drawable.banner2, R.drawable.banner4};


        SliderAdapter adapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();
    }

    private void dealOfTheDay() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        list = new ArrayList<>();
        collectionReference.document("Beds").collection("local")
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

                            }

                            fourItemViewAdapter = new FourItemViewAdapter(list, getContext());
                            recyclerView.setAdapter(fourItemViewAdapter);
                            homepageprogess.setVisibility(View.GONE);
                            constraintLayout2.setVisibility(View.VISIBLE);


                        } else {
                            String e = task.getException().toString();
                            Toast.makeText(getContext(), "Failed" + e, Toast.LENGTH_SHORT).show();
                        }


                    }

                });
    }


}