package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillsAdapter;
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;


public class List_Bill_Fragment extends Fragment {
    ArrayList<Bill> list;
    BillDAO billDAO;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list__bill_, container, false);

        recyclerView = view.findViewById(R.id.rcl_list_bill);
        billDAO = new BillDAO(getContext());
        LoadData();
        return view;
    }
    private void LoadData(){
        list = billDAO.getListService();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        BillsAdapter adapter = new BillsAdapter(list, getContext(), billDAO);
        recyclerView.setAdapter(adapter);
    }
}