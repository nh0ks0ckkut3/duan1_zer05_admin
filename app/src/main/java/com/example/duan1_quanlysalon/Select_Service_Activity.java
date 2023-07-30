package com.example.duan1_quanlysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.duan1_quanlysalon.adapter.ListSelectProductAdapter;
import com.example.duan1_quanlysalon.adapter.ListSelectServiceAdapter;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class Select_Service_Activity extends AppCompatActivity {
    ArrayList<Service> list;
    ServiceDAO serviceDAO;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        gridView = findViewById(R.id.list_select_service);
        serviceDAO = new ServiceDAO(Select_Service_Activity.this);
        LoadData();
    }
    private void LoadData(){
        list = serviceDAO.getListService();
        ListSelectServiceAdapter adapter = new ListSelectServiceAdapter(list, R.layout.item_select_service,this, serviceDAO);
        gridView.setAdapter(adapter);
    }
}