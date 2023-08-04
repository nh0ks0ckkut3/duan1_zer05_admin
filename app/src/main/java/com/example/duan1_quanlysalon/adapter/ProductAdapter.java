package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.fragment.ProductDetailFragment;
import com.example.duan1_quanlysalon.fragment.ServiceDetailFragment;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> list;
    Context context;


    public ProductAdapter(ArrayList<Product> list, Context context){
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvID.setText("Mã SP: "+list.get(position).getId());
        holder.tvPrice.setText("Giá: " + String.valueOf(list.get(position).getPrice())+",000 VNĐ");
        holder.tvAmount.setText("SL: " + list.get(position).getAmount() +" "+ list.get(position).getUnit());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).replayFragment(new ProductDetailFragment(list.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvID, tvPrice, tvAmount;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvID = itemView.findViewById(R.id.tvID);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            item = itemView.findViewById(R.id.item);
        }
    }
}