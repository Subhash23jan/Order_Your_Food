package com.example.orderyourfood.cartmanagement;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.orderyourfood.R;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderImageHolder> {

    ArrayList<OrderCart>ordercart=new ArrayList<>();

    public OrderListAdapter(ArrayList<OrderCart> ordercart) {
        this.ordercart = ordercart;
    }

    @NonNull
    @Override
    public OrderImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        return new OrderImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderImageHolder holder, int position) {
        holder.imageView.setImageResource(ordercart.get(position).getImage_id());
        holder.textViewName.setText(ordercart.get(position).getFoodName().toString());
        holder.textViewPrice.setText(String.valueOf("₹"+ordercart.get(position).getPrice()+".00"));
        holder.quantity.setText(String.valueOf(ordercart.get(position).getCounts()));
    }

    @Override
    public int getItemCount() {
        return ordercart.size();
    }

    public static class OrderImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        TextView quantity;
        public OrderImageHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.foodName);
            textViewPrice=itemView.findViewById(R.id.foodPrice);
            imageView=itemView.findViewById(R.id.images);
            quantity=itemView.findViewById(R.id.quantity);
        }
    }
}
