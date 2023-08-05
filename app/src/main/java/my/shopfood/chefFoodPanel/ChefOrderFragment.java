package my.shopfood.chefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import my.shopfood.R;

public class ChefOrderFragment  extends Fragment {
    TextView OrdertobePrepare,PreparedOrders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Orders");
        View v = inflater.inflate(R.layout.fragment_chef_orders, null);
        OrdertobePrepare=(TextView)v.findViewById(R.id.ordertobe);
        PreparedOrders=(TextView)v.findViewById(R.id.prepareorder);

        OrdertobePrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Chef_Order_Tobe_Perpared.class);
                startActivity(i);
            }
        });

        PreparedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Chef_Prepared_Order.class);
                startActivity(intent);
            }
        });


        return v;
    }
}
