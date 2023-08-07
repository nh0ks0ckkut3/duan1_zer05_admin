package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;

public class listProductCheckoutAdapter   extends RecyclerView.Adapter<listProductCheckoutAdapter.ViewHolDer> {
    private Context context;
    private ArrayList<Product> list;


    public listProductCheckoutAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public listProductCheckoutAdapter.ViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sv_pro_checkout, parent, false);
        return new listProductCheckoutAdapter.ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listProductCheckoutAdapter.ViewHolDer holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvPrice.setText(list.get(position).getPrice()+".000");
        holder.tvTotalPrice.setText(list.get(position).getPrice()+".000");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvTotalPrice;

        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
