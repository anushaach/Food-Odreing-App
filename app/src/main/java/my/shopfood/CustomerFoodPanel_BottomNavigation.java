package my.shopfood;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.shopfood.customerFoodPanel.CustomerCartFragment;
import my.shopfood.customerFoodPanel.CustomerHomeFragment;
import my.shopfood.customerFoodPanel.CustomerProfileFragment;
import my.shopfood.customerFoodPanel.CustomerTrackFragment;

public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected  void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food_panel_bottom_navigation);
        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name=getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if (name!=null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadragment(new CustomerHomeFragment());
            } else if (name.equalsIgnoreCase("Preparingpage")) {
                loadragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("DeliveryOrderpage")) {
                loadragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("Thankyoupage")) {
                loadragment(new CustomerHomeFragment());
            }
        }else {
            loadragment(new CustomerHomeFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.cust_Home:
                fragment=new CustomerHomeFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cart:
                fragment=new CustomerCartFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cust_profile:
                fragment=new CustomerProfileFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.track:
                fragment=new CustomerTrackFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.Cust_order:
                fragment=new CustomerHomeFragment();
                break;
        }


        return loadragment(fragment);
    }

    private boolean loadragment(Fragment fragment) {
        if(fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}
