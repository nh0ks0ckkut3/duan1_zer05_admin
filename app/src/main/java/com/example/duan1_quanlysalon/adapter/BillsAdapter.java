package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder>{
    ArrayList<Bill> list;
    Context context;
    BillDAO billDAO;

    public BillsAdapter(ArrayList<Bill> list, Context context, BillDAO billDAO) {
        this.list = list;
        this.context = context;
        this.billDAO = billDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdapter.ViewHolder holder, int position) {
        holder.txtSDT.setText(list.get(position).getPhoneNumberCustomer());
        holder.txtTongtien.setText(String.valueOf(list.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSDT, txtUse, txtTongtien;
        Button btnChitiet, btnThanhtoan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSDT = itemView.findViewById(R.id.item_bill_std);
            txtUse = itemView.findViewById(R.id.item_bill_useService);
            txtTongtien = itemView.findViewById(R.id.item_bill_totalBill);
            btnChitiet = itemView.findViewById(R.id.btn_chitiet_itembill);
            btnThanhtoan = itemView.findViewById(R.id.btn_thanhtoan_itembill);

        }
    }
}
