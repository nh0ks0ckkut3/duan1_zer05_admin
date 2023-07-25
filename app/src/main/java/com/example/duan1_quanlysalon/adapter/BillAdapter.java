package com.example.duan1_quanlysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolDer holder, int position) {

        holder.tvName.setText("" + list.get(position).getUserNameEmployee());
        holder.tvPhone.setText("" + list.get(position).getPhoneNumberCustomer());
        holder.tvToast.setText("" + list.get(position).getStatus());
        holder.tvTime.setText("Giờ book:" + list.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder{
        TextView tvid, tvName, tvPhone, tvToast, tvTime;
        Button btnclick;
        public ViewHolDer(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvToast = itemView.findViewById(R.id.tvToast);
            tvTime = itemView.findViewById(R.id.tvTime);
            //nghia

        }
    }
}
