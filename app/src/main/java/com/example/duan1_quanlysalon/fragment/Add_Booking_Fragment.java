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
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;

public class Add_Booking_Fragment extends Fragment {

    RecyclerView recyclerView;

    EmployeeDAO employeeDAO;

    ArrayList<Employee> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);
        recyclerView = view.findViewById(R.id.rcl_stylist_add_booking);
        employeeDAO = new EmployeeDAO(getContext());
        LoadData();
        return view;
    }
    private void LoadData(){
        list = employeeDAO.getListEmployeeStylist();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListStylistAdapter adapter = new ListStylistAdapter(list, getContext(), employeeDAO);
        recyclerView.setAdapter(adapter);
    }
}