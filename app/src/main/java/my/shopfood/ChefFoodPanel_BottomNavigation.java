package my.shopfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import my.shopfood.chefFoodPanel.ChefHomeFragment;
import my.shopfood.chefFoodPanel.ChefOrderFragment;
import my.shopfood.chefFoodPanel.ChefPendingOrderFragment;
import my.shopfood.chefFoodPanel.ChefProfileFragment;

public class ChefFoodPanel_BottomNavigation extends AppCompatActivity  implements OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_food_panel_bottom_navigation);
    BottomNavigationView navigationView=findViewById(R.id.chef_bottom_navigation);
    navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.chefHome:
                 fragment = new ChefHomeFragment();
                break;
            case R.id.pendingOrders:
                fragment=new ChefPendingOrderFragment();
                break;
            case R.id.Orders:
                fragment=new ChefOrderFragment();
                break;
            case R.id.chefProfile:
                fragment=new ChefProfileFragment();
                break;

        }
        return loadcheffragment(fragment);
    }

    private boolean loadcheffragment(Fragment fragment) {
        if (fragment != null){
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
         return true;
        }
        return false;
    }
}