package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ViewHolder>{


    ArrayList<Product> list;
    Context context;
    ProductDAO productDAO;



    public ProductAdapter(ArrayList<Product> list, Context context, ProductDAO productDAO){
        this.list = list;
        this.context = context;
        this.productDAO = productDAO;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ds_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(String.valueOf(list.get(position).getName()));
        holder.tvmasp.setText(String.valueOf(list.get(position).getId()));
        holder.tvPrice.setText(list.get(position).getPrice());
        holder.tvSL.setText(list.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView  tvName, tvmasp, tvPrice, tvSL;
        ImageView ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvmasp = itemView.findViewById(R.id.tvmasp);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSL = itemView.findViewById(R.id.tvSL);
        }
    }
}
