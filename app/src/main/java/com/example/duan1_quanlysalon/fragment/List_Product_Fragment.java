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
import com.example.duan1_quanlysalon.adapter.ProductAdapter;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;


public class List_Product_Fragment extends Fragment {
    RecyclerView recyclerView;

    ProductDAO productDAO;

    ArrayList<Product> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list__product_, container, false);
        recyclerView = view.findViewById(R.id.rcl_list_product);
        productDAO = new ProductDAO(getContext());
        LoadData();
        return view;
    }
    private void LoadData(){
        list = productDAO.getListService();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(list, getContext(), productDAO);
        recyclerView.setAdapter(adapter);
    }
}