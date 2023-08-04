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

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ItemStylistClick;

import java.util.ArrayList;

public class ListStylistAdapter extends RecyclerView.Adapter<ListStylistAdapter.ViewHolder> {
    ArrayList<Employee> list;
    Context context;
    private ItemStylistClick itemClick;
    private String userNameEmployeeClick;


    public ListStylistAdapter(ArrayList<Employee> list, Context context,String userNameEmployeeClick, ItemStylistClick itemClick){
        this.list = list;
        this.context = context;
        this.itemClick = itemClick;
        this.userNameEmployeeClick = userNameEmployeeClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_stylist_add_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvClassify.setText(list.get(position).getClassify());

        if(userNameEmployeeClick.equals(list.get(position).getUserName())){
            holder.itemStylist.setBackgroundResource(R.color.green);
        }else{
            holder.itemStylist.setBackgroundResource(R.color.white);
        }
        holder.itemStylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClickBook(list.get(position));
                userNameEmployeeClick = list.get(position).getUserName();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvClassify;
        ImageView ivAvatar;
        LinearLayout itemStylist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvClassify = itemView.findViewById(R.id.tvClassify);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            itemStylist = itemView.findViewById(R.id.itemStylist);
        }
    }
}
