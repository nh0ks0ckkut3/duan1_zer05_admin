package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class List_Employee_Fragment extends Fragment {
    RecyclerView recyclerView;

    EmployeeDAO employeeDAO;

    ArrayList<Employee> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list__employee_, container, false);
        recyclerView = view.findViewById(R.id.rcl_list_employee);
        employeeDAO = new EmployeeDAO(getContext());
        LoadData();
        return view;
    }

    private void LoadData(){
        list = employeeDAO.getListEmployee();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        EmployeeAdapter adapter = new EmployeeAdapter(list, getContext(), employeeDAO);
        recyclerView.setAdapter(adapter);
    }
}