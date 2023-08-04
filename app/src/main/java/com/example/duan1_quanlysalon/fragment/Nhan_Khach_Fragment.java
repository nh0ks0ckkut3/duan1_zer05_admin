package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;


public class Nhan_Khach_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);

        return view;
    }
}