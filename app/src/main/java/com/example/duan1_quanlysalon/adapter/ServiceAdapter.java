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
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    ArrayList<Service> list;
    Context context;
    ServiceDAO serviceDAO;


    public ServiceAdapter(ArrayList<Service> list, Context context, ServiceDAO serviceDAO){
        this.list = list;
        this.context = context;
        this.serviceDAO = serviceDAO;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTendv.setText(list.get(position).getName());
        holder.txtGia.setText(String.valueOf(list.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTendv, txtGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTendv = itemView.findViewById(R.id.tendv_item_list_service);
            txtGia = itemView.findViewById(R.id.gia_item_list_service);

        }
    }
}