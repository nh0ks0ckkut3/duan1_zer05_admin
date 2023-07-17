package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.adapter.ServiceAdapter;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;


public class List_Service_Fragment extends Fragment {

    RecyclerView recyclerView;

    ServiceDAO serviceDAO;

    ArrayList<Service> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list__service_, container, false);
        recyclerView = view.findViewById(R.id.rcl_list_service);
        serviceDAO = new ServiceDAO(getContext());
        LoadData();

        return view;
    }

    private void LoadData(){
        list = serviceDAO.getListService();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ServiceAdapter adapter = new ServiceAdapter(list, getContext(), serviceDAO);
        recyclerView.setAdapter(adapter);
    }
}