package com.example.duan1_quanlysalon.fragment;

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

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapter;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.model.Bill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Booking_Fragment extends Fragment {
    RecyclerView recyclerView;
    BillDAO billDAO;
    Bundle bundle;
    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        recyclerView = view.findViewById(R.id.recyclerView);

        billDAO = new BillDAO(getContext());
        loadData();
        return view;
    }
    public void loadData() {
        ArrayList<Bill> list = billDAO.getListBooking();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        BillAdapter adapter = new BillAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

}