package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListSelectProductAdapter;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;


public class Select_Product_Fragment extends Fragment {
    ArrayList<Product> list;
    ProductDAO productDAO;
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__product_, container, false);
        gridView = view.findViewById(R.id.list_select_product);
        productDAO = new ProductDAO(getContext());
        LoadData();

        return view;
    }
    private void LoadData(){
        list = productDAO.getListService();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        gridView.setLayoutManager(linearLayoutManager);
        ListSelectProductAdapter adapter = new ListSelectProductAdapter(list, R.layout.item_select_product,getContext(), productDAO);
        gridView.setAdapter(adapter);
    }
}