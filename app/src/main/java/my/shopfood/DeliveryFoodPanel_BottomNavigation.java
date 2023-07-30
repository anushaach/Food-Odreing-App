package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.shopfood.customerFoodPanel.CustomerCartFragment;
import my.shopfood.customerFoodPanel.CustomerHomeFragment;
import my.shopfood.customerFoodPanel.CustomerProfileFragment;
import my.shopfood.customerFoodPanel.CustomerTrackFragment;
import my.shopfood.deliveryFoodPanel.DeliveryPenndingOderFragment;
import my.shopfood.deliveryFoodPanel.DeliveryShipOderFragment;

public class DeliveryFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_food_panel_bottom_navigation);
        BottomNavigationView navigationView=findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name= getIntent().getStringExtra("PAGE");
        if (name!=null){
            if (name.equalsIgnoreCase("Deliveryorderpage")){
                loaddeliveryfragment(new DeliveryPenndingOderFragment());
            }
            else{
                loaddeliveryfragment(new DeliveryPenndingOderFragment());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.shiporders:
                    fragment=new DeliveryShipOderFragment();
                    break;
            }
            switch (item.getItemId()){
                case R.id.pendingOrders:
                    fragment=new DeliveryPenndingOderFragment();
                    break;
            }



            return loaddeliveryfragment(fragment);
        }



    private boolean loaddeliveryfragment(Fragment fragment) {
        if(fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}