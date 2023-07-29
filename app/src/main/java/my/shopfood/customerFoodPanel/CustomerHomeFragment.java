package my.shopfood.customerFoodPanel;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.shopfood.R;
import my.shopfood.UpdateDishModel;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private List<UpdateDishModel>updateDishModelList;
    private CustomerHomeAdapter adapter;
    DatabaseReference dataa,databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("Home");
        recyclerView=v.findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList=new ArrayList<>();
        swipeRefreshLayout=(SwipeRefreshLayout) v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.defaultColor,R.color.Red);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataa= FirebaseDatabase.getInstance().getReference("Customer").child(userid);
                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Customer custo=snapshot.getValue(Customer.class);
                         state = custo.getState();
                        city = custo.getCity();
                        Area = custo.getArea();
                        customermenu();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        return v;
    }

    @Override
    public void onRefresh() {
        customermenu();
        
    }

    private void customermenu() {
        swipeRefreshLayout.setRefreshing(true);
        databaseReference=FirebaseDatabase.getInstance().getReference("FoodDetails").child(Sate).child(Area).child(City);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateDishModelList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    for (DataSnapshot snapshot2:snapshot.getChildren()){
                        UpdateDishModel updateDishModel=snapshot2.getValue(UpdateDishModel.class);
                        updateDishModelList.add(updateDishModel);
                    }
                }
                adapter=new CustomerHomeAdapter(getContext(),updateDishModelList);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }
}
