package com.example.duan1_quanlysalon.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapter;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Bill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class Booking_Fragment extends Fragment {
    RecyclerView rcvKhachDangCho,rcvKhachChuaToi,rcvKhachDangPhucVu;
    BillDAO billDAO;
    Bundle bundle;
    Intent intent;
    private ListView listView;
    ProductDAO productDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_, container, false);
        rcvKhachDangCho = view.findViewById(R.id.rcvKhachDangCho);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        rcvKhachDangCho = view.findViewById(R.id.rcvKhachDangCho);

        billDAO = new BillDAO(getContext());

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog();
            }
        });
        return view;
    }


    private void showDiaLog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_bill, null);
        EditText edtPhone  = view.findViewById(R.id.edtPhone);
        EditText edtName  = view.findViewById(R.id.edtName);
        EditText edtService  = view.findViewById(R.id.edtidService);
        EditText edtPrice  = view.findViewById(R.id.edtPrice);
        builder.setView(view);


        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone = edtPhone.getText().toString();
                String name = edtName.getText().toString();
                String idService = edtService.getText().toString();
                String price = edtPrice.getText().toString();

                boolean check = billDAO.addBill(phone, name, idService, price);
                if(check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void loadData() {
        ArrayList<Bill> list = billDAO.getListBooking();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvKhachDangCho.setLayoutManager(linearLayoutManager);
        BillAdapter adapter = new BillAdapter(getContext(), list);
        rcvKhachDangCho.setAdapter(adapter);
    }

}
