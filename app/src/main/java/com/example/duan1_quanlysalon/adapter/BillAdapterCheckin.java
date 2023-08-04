package com.example.duan1_quanlysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;

public class BillAdapterCheckin extends RecyclerView.Adapter<BillAdapterCheckin.ViewHolDer>{
    private Context context;
    private ArrayList<Bill> list;


    public BillAdapterCheckin(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_checkin, parent, false);
        return new ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolDer holder, int position) {
        holder.tvPhoneCustomer.setText(list.get(position).getPhoneNumberCustomer());
        holder.tvNameCustomer.setText(list.get(position).getNameCustomer());
//        int countTime = 0;
//        run(countTime);
//        holder.tvCountTime.setText(String.valueOf(countTime));
        holder.tvBookTime.setText(list.get(position).getBookTime());
        holder.tvNhanKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder{
        TextView tvNameCustomer, tvPhoneCustomer, tvCountTime,tvBookTime, tvNhanKhach;
        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            tvNameCustomer = itemView.findViewById(R.id.tvNameCustomer);
            tvPhoneCustomer = itemView.findViewById(R.id.tvPhoneCustomer);
            tvCountTime = itemView.findViewById(R.id.tvCountTime);
            tvBookTime = itemView.findViewById(R.id.tvBookTime);
            tvNhanKhach = itemView.findViewById(R.id.tvNhanKhach);
        }
    }
    public void run(int countTime) {
        try {
            while (true) {
                Thread.sleep(5000); // ??
                countTime+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
