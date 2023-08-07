package my.shopfood.chefFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.shopfood.R;

public class ChefOrderDishesAdapter extends RecyclerView.Adapter<ChefOrderDishesAdapter.ViewHolder> {
    private Context mcontext;
    private List<ChefPendingOrders> chefPendingOrderslist;

    public ChefOrderDishesAdapter(Context context, List<ChefPendingOrders> chefPendingOrderslist) {
        this.chefPendingOrderslist = chefPendingOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_order_dishes, parent, false);
        return new ChefOrderDishesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefPendingOrders chefPendingOrders = chefPendingOrderslist.get(position);
        holder.dishname.setText(chefPendingOrders.getDishName());
        holder.price.setText("Price:Rs " + chefPendingOrders.getPrice());
        holder.quantity.setText("× " + chefPendingOrders.getDishQuantity());
        holder.totalprice.setText("Total:Rs " + chefPendingOrders.getTotalPrice());


    }

    @Override
    public int getItemCount() {
        return chefPendingOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.DN);
            price = itemView.findViewById(R.id.PR);
            totalprice = itemView.findViewById(R.id.TR);
            quantity = itemView.findViewById(R.id.QY);
        }
    }
}
