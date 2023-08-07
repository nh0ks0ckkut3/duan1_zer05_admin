package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Bill;


public class Payment_Complete_Fragment extends Fragment {

    private TextView tvLoaiGiaoDich, tvDienGiai, tvTotalPrice, tvTime;
    private Bill billCheckout;
    private int totalPrice;
    private String loaiGD;

    public Payment_Complete_Fragment(Bill billCheckout, int totalPrice, String loaiGiaoDich) {
        this.billCheckout = billCheckout;
        this.totalPrice = totalPrice;
        this.loaiGD = loaiGiaoDich;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment__complete_, container, false);
        tvLoaiGiaoDich = view.findViewById(R.id.tvLoaiGiaoDich);
        tvDienGiai = view.findViewById(R.id.tvDienGiai);
        tvTime = view.findViewById(R.id.tvTime);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        if(loaiGD.equals("cash")){
            tvLoaiGiaoDich.setText("Tiền mặt");
            tvDienGiai.setVisibility(View.GONE);
        }else{
            tvLoaiGiaoDich.setText("chuyển khoản");
            tvDienGiai.setVisibility(View.VISIBLE);
        }
        tvTime.setText(billCheckout.getTime());
        tvTotalPrice.setText(totalPrice+"000, VNĐ");
        return view;
    }
}