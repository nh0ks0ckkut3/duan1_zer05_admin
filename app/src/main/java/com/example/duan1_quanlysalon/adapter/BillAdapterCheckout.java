package com.example.duan1_quanlysalon.adapter;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.fragment.Bill_Detail_Fragment;
import com.example.duan1_quanlysalon.fragment.Confirm_Payment_Fragment;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillAdapterCheckout extends RecyclerView.Adapter<BillAdapterCheckout.ViewHolder>{
    ArrayList<Bill> list;
    Context context;
    Boolean isReady;

    public BillAdapterCheckout(ArrayList<Bill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill_checkout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapterCheckout.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtSDT.setText(list.get(position).getPhoneNumberCustomer());
        holder.txtTongtien.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.tvID.setText("Mã HĐ: "+list.get(position).getId());
        holder.txtUse.setText(list.get(position).getSumService()+" dịch vụ và "+list.get(position).getSumProduct()+" sản phẩm");
        holder.txtTongtien.setText(list.get(position).getNameCustomer());

        holder.btnChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReady = false;
                ((MainActivity)context).replayFragment(new Bill_Detail_Fragment(list.get(position), isReady));
            }
        });
        holder.btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReady = true;
                ((MainActivity)context).replayFragment(new Bill_Detail_Fragment(list.get(position), isReady));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSDT, txtUse, txtTongtien,tvID;
        Button btnChitiet, btnThanhtoan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            txtSDT = itemView.findViewById(R.id.item_bill_std);
            txtUse = itemView.findViewById(R.id.item_bill_useService);
            txtTongtien = itemView.findViewById(R.id.item_bill_totalBill);
            btnChitiet = itemView.findViewById(R.id.btn_chitiet_itembill);
            btnThanhtoan = itemView.findViewById(R.id.btn_thanhtoan_itembill);

        }
    }

}
