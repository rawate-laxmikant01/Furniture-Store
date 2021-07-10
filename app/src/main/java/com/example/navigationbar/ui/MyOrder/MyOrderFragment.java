package com.example.navigationbar.ui.MyOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.Adapter.OrderAdapter;
import com.example.navigationbar.MainActivity;
import com.example.navigationbar.Model.OrderModel;
import com.example.navigationbar.R;
import com.example.navigationbar.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrderFragment extends Fragment {

    private MyOrderViewModel galleryViewModel;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference,ref;
    private RecyclerView recyclerView;
    private ArrayList<OrderModel> orderlist;
    LinearLayout empty,myorder;
    ProgressBar orderprogess;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MyOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myorder, container, false);

        recyclerView = root.findViewById(R.id.order_recyclerview_id);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        empty=root.findViewById(R.id.empty_order_id);

        orderprogess=root.findViewById(R.id.orderprogress);
        reference = firebaseDatabase.getReference("userdata");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myorder=root.findViewById(R.id.myorder_linear);

        Button btn_ordernow=root.findViewById(R.id.orderNow_id);
        btn_ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

//-----------------------------------------------------------------------------------

//        reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Order> olist = new ArrayList<>();
//                List<CartModel> list = new ArrayList<>();
//
//                    for (DataSnapshot orderSnapshot : snapshot.child("order").getChildren()) {
//
//                        //
//                        {
//                            if (orderSnapshot.exists()) {
//                                // orderlist.add(os.getValue(CartModel.class));
//
//                                olist.add(orderSnapshot.getValue(Order.class));
//                                for (DataSnapshot os : orderSnapshot.child("orders").getChildren()) {
//                                    if(os.exists()){
//                                        list.add(os.getValue(CartModel.class));
//                                     //   Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
//                                    }
////                                    else {
////                                      //  Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
////                                    }
//
//                                }
//
//                              //  Toast.makeText(getContext(), "Orders present", Toast.LENGTH_SHORT).show();
//                            }
////                            else {
////                                //Toast.makeText(getContext(), "order not exist", Toast.LENGTH_SHORT).show();
////
////                            }
//                            Collections.reverse(list);
//                            Collections.reverse(olist);
//                         user_order_adapter = new User_order_adapter(olist, getContext(), list);
//
//                        // user_order_adapter = new User_order_adapter(olist,User_Orders.this);
//                    }
//
//
//                    }
//                //if (user_order_adapter.getItemCount() != 0) {
//                    recyclerView.setAdapter(user_order_adapter);
////                    empty.setVisibility(View.GONE);
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "unable to load order" + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//-------------------------------------------------------

        empty.setVisibility(View.INVISIBLE);

        orderlist = new ArrayList<>();

        reference.child(auth.getCurrentUser().getUid()).child("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot order:dataSnapshot.getChildren()){
                        OrderModel m = order.getValue(OrderModel.class);
                        orderlist.add(m);
                    }
                }

                orderprogess.setVisibility(View.INVISIBLE);
                OrderAdapter orderAdapter = new OrderAdapter(orderlist, getContext());
              if (orderAdapter.getItemCount() != 0) {
                    recyclerView.setAdapter(orderAdapter);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    orderprogess.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "unable to load order" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }
}