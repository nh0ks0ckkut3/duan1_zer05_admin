package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;

public class ThuNhapFragment extends Fragment {

    TextView tvSalary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_nhap, container, false);

        tvSalary = view.findViewById(R.id.tvSalary);
        tvSalary.setText(((MainActivity)getContext()).currentUser.getSalary()+" Dollar $");

        return view;
    }
}