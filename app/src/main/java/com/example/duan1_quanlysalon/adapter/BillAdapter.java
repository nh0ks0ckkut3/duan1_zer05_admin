package com.example.duan1_quanlysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolDer>{
    private Context context;
    private ArrayList<Bill> list;
    private BillDAO billDAO;


    public BillAdapter(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolDer holder, int position) {
        holder.txtphoneNumber.setText("Số ĐT: " + list.get(position).getPhoneNumberCustomer());
        holder.txtName.setText("Tên Khách: " + list.get(position).getUserNameEmployee());
        holder.txtidService.setText("Tên DV: " + list.get(position).getIdService());
        holder.txtTotalPrice.setText("Giá: " + list.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder{
        TextView txtphoneNumber, txtName, txtidService,txtTotalPrice;
        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            txtphoneNumber = itemView.findViewById(R.id.txtphoneNumber);
            txtName = itemView.findViewById(R.id.txtName);
            txtidService = itemView.findViewById(R.id.txtidService);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);

            //nghia

        }
    }
}
