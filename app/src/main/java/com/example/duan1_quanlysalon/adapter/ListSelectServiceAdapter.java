package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ListSelectServiceAdapter extends RecyclerView.Adapter<ListSelectServiceAdapter.ViewHolder> {
    ArrayList<Service> list;
    Context context;
    ArrayList<Integer> listIDSevice;

    public ListSelectServiceAdapter(ArrayList<Service> list, Context context){
        this.list = list;
        this.context = context;
        this.listIDSevice = ((MainActivity)context).getListIDServiceSelected();
    }


    @NonNull
    @Override
    public ListSelectServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_select_service, parent, false);
        return new ListSelectServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSelectServiceAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvNameService.setText(list.get(position).getName());
        holder.tvPrice.setText(String.valueOf(list.get(position).getPrice()));
        for(int i = 0; i < listIDSevice.size(); i++){
            if((int)listIDSevice.get(i) == list.get(position).getId()){
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.btnOk.setVisibility(View.GONE);
            }
        }
        holder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listIDSevice.add(list.get(position).getId());
                ((MainActivity)context).setListIDServiceSelected(listIDSevice);
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.btnOk.setVisibility(View.GONE);
            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Integer x : listIDSevice){
                    if((int)x == list.get(position).getId()){
                        listIDSevice.remove(x);
                        ((MainActivity)context).setListIDServiceSelected(listIDSevice);
                        break;
                    }
                }

                holder.btnCancel.setVisibility(View.GONE);
                holder.btnOk.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameService, tvPrice, btnOk;
        LinearLayout btnCancel;
        ImageView ivAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameService = itemView.findViewById(R.id.tvNameService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnOk = itemView.findViewById(R.id.btnOke);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
        }
    }

}