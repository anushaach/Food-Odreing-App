package my.shopfood.customerFoodPanel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import my.shopfood.UpdateDishModel;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {
    private Context mcontext;
    private List<UpdateDishModel>updateDishModelList;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context , List<UpdateDishModel>updateDishModelList){
        this.updateDishModelList = updateDishModelList;
        this.mcontext = context;


    }

    @NonNull
    @Override
    public CustomerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHomeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView Dishname, Price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            Dishname = itemView.findViewById(R.id.dishname);
            Price = itemView.findViewById(R.id.dishprice);


        }
    }
}
