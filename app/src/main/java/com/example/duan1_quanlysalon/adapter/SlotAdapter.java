package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.ItemSlotClick;
import com.example.duan1_quanlysalon.model.Slot;

import java.util.ArrayList;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Slot> list;
    private String slotPick;
    private ItemSlotClick slotClick;
    public SlotAdapter(Context context, ArrayList<Slot> list, String slotPick, ItemSlotClick slotClick) {
        this.context = context;
        this.list = list;
        this.slotClick = slotClick;
        this.slotPick = slotPick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_slot,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            java.util.Date date = new java.util.Date();
            holder.tvTime.setText(list.get(position).getTime());
            if(list.get(position).getTime().equals(slotPick)){
                holder.tvTime.setBackgroundResource(R.color.green);
            }
            if(Integer.parseInt(list.get(position).getTime().substring(0,2))<date.getHours()){
                holder.tvTime.setBackgroundResource(R.color.gray);
                list.get(position).setReady(false);
            }
            else if(Integer.parseInt(list.get(position).getTime().substring(0,2))==date.getHours() &&
                    Integer.parseInt(list.get(position).getTime().substring(3,4))<date.getMinutes()){
                holder.tvTime.setBackgroundResource(R.color.gray);
                list.get(position).setReady(false);
            }

        holder.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).isReady()) {

                    slotPick = holder.tvTime.getText().toString();
                    holder.tvTime.setBackgroundResource(R.color.green);
                    slotClick.onClickSlot(list.get(position));
                    loadData();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
    private void loadData(){
        this.notifyDataSetChanged();
    }
}