package com.example.duan1_quanlysalon.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_quanlysalon.R;


public class Confirm_Payment_Fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm__payment_, container, false);
        TextView btntructiep = view.findViewById(R.id.btn_typepayment_tructiep);
        TextView btnchuyenkhoan = view.findViewById(R.id.btn_typepayment_chuyenkhoan);

        TextView tvtkd = view.findViewById(R.id.textView_tienkhacdua);
        TextView tvtt = view.findViewById(R.id.textView_tienthua);
        TextView sotiendua = view.findViewById(R.id.payment_detail_bill_tienkhachdua);
        TextView sotienthua = view.findViewById(R.id.payment_detail_bill_tienthua);

        ImageView qr = view.findViewById(R.id.qrcode);

        //nhấn nút ẩn qr code hiện 4 textview
        btntructiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntructiep.setBackgroundResource(R.drawable.btn_typepayment_select);
                btnchuyenkhoan.setBackgroundResource(R.drawable.btn_typepayment_nonselect);
                tvtkd.setVisibility(View.VISIBLE);
                tvtt.setVisibility(View.VISIBLE);
                sotiendua.setVisibility(View.VISIBLE);
                sotienthua.setVisibility(View.VISIBLE);
                qr.setVisibility(View.GONE);
            }
        });

        //ấn nút hiện qr code ẩn 4 textview
        btnchuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntructiep.setBackgroundResource(R.drawable.btn_typepayment_nonselect);
                btnchuyenkhoan.setBackgroundResource(R.drawable.btn_typepayment_select);
                tvtkd.setVisibility(View.GONE);
                tvtt.setVisibility(View.GONE);
                sotiendua.setVisibility(View.GONE);
                sotienthua.setVisibility(View.GONE);
                qr.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}