package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.fragment.ServiceDetailFragment;
import com.example.duan1_quanlysalon.model.ItemClickGetDetailService;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    ArrayList<Service> list;
    Context context;
    private ItemClickGetDetailService itemClick;


    public ServiceAdapter(ArrayList<Service> list, Context context,ItemClickGetDetailService itemClick){
        this.list = list;
        this.context = context;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvPrice.setText("Giá: "+list.get(position).getPrice()+",000 VNĐ");
        holder.tvID.setText("Mã dịch vụ: "+list.get(position).getId());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(list.get(position));
                ((MainActivity)context).replayFragment(new ServiceDetailFragment(list.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvID, tvPrice;
        ImageView ivAvatar;
        CardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvID = itemView.findViewById(R.id.tvID);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            item = itemView.findViewById(R.id.item);

        }
    }
}